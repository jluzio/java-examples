package com.example.java.playground.features.concurrency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
class DeadlockTest {

  @Data
  static class Friend {

    private final String name;
    private int bowsCompleted = 0;

    public synchronized void bow(Friend bower) {
      log.debug("{}: {} has bowed to me!", this.name, bower.getName());
      bower.bowBack(this);
      bowsCompleted++;
    }

    public synchronized void bowBack(Friend bower) {
      log.debug("{}: {} has bowed back to me!", this.name, bower.getName());
    }
  }

  @Test
  void test() throws InterruptedException {
    var alphonse = Mockito.spy(new Friend("Alphonse"));
    var gaston = Mockito.spy(new Friend("Gaston"));
    new Thread(() -> alphonse.bow(gaston)).start();
    new Thread(() -> gaston.bow(alphonse)).start();

    Thread.sleep(100L);

    verify(alphonse).bowBack(any());
    verify(gaston).bowBack(any());
    assertThat(alphonse.getBowsCompleted()).isZero();
    assertThat(gaston.getBowsCompleted()).isZero();
  }
}