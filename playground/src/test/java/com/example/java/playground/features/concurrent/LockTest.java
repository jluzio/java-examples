package com.example.java.playground.features.concurrent;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Try;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class LockTest {

  @Data
  static class Friend {

    private final String name;
    private final Lock lock = new ReentrantLock();
    private int completedBows = 0;

    public boolean impendingBow(Friend bower) {
      boolean myLock = false;
      boolean yourLock = false;
      try {
        myLock = lock.tryLock();
        yourLock = bower.lock.tryLock();
      } finally {
        if (!(myLock && yourLock)) {
          if (myLock) {
            lock.unlock();
          }
          if (yourLock) {
            bower.lock.unlock();
          }
        }
      }
      return myLock && yourLock;
    }

    public void bow(Friend bower) {
      if (impendingBow(bower)) {
        try {
          log.info("{}: {} has bowed to me!", this.name, bower.getName());
          bower.bowBack(this);
          completedBows++;
        } finally {
          lock.unlock();
          bower.lock.unlock();
        }
      } else {
        log.info("{}: {} started to bow to me, but saw that I was already bowing to him.",
            this.name, bower.getName());
      }
    }

    public void bowBack(Friend bower) {
      log.info("{}: {} has bowed back to me!", this.name, bower.getName());
    }
  }

  @Data
  static class BowLoop implements Runnable {

    private final Friend bower;
    private final Friend bowee;

    public void run() {
      Random random = new Random();
      boolean exit = false;
      while (!exit) {
        try {
          Thread.sleep(random.nextInt(10));
          bowee.bow(bower);
        } catch (InterruptedException e) {
          exit = true;
        }
      }
    }
  }

  @Test
  void test() throws InterruptedException {
    var alphonse = new Friend("Alphonse");
    var gaston = new Friend("Gaston");
    var threads = List.of(
        new Thread(new BowLoop(alphonse, gaston)),
        new Thread(new BowLoop(gaston, alphonse))
    );

    threads.forEach(Thread::start);
    Thread.sleep(200L);
    threads.forEach(Thread::interrupt);
    threads.forEach(t -> Try.run(t::join));

    assertThat(alphonse.getCompletedBows()).isPositive();
    assertThat(gaston.getCompletedBows()).isPositive();
    log.info("Alphonse: {}", alphonse.getCompletedBows());
    log.info("Gaston: {}", gaston.getCompletedBows());
  }
}
