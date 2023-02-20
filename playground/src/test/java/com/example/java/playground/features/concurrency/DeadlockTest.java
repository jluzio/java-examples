package com.example.java.playground.features.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

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
      log.info("{}: {} has bowed to me!", this.name, bower.getName());
      bower.bowBack(this);
      bowsCompleted++;
    }

    public synchronized void bowBack(Friend bower) {
      log.info("{}: {} has bowed back to me!", this.name, bower.getName());
    }
  }

  @Test
  void test() throws InterruptedException {
    var alphonse = Mockito.spy(new Friend("Alphonse"));
    var gaston = Mockito.spy(new Friend("Gaston"));
    var threadAlphonse = new Thread(() -> alphonse.bow(gaston));
    var threadGaston = new Thread(() -> gaston.bow(alphonse));

    threadAlphonse.start();
    threadGaston.start();

    Thread.sleep(100L);

    threadAlphonse.interrupt();
    threadGaston.interrupt();

    assertThat(alphonse.getBowsCompleted()).isZero();
    assertThat(gaston.getBowsCompleted()).isZero();
  }
}