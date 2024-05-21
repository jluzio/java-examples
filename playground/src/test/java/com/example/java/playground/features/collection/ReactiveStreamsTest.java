package com.example.java.playground.features.collection;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ReactiveStreamsTest {

  public record User(String name, String mail) {

  }

  @Test
  void test() {
    log.info("Reactive Streams");
    SubmissionPublisher<User> publisher = new SubmissionPublisher<>();

    publisher.subscribe(new MySubscriber("sub-1", 2));
    publisher.subscribe(new MySubscriber("sub-2", 1));

    IntStream.range(1, 10).forEach(
        v -> publisher.submit(
            new User("name-" + v, String.format("name-%s@example.org", v)))
    );
    publisher.close();

    Predicate<MySubscriber> mySubscriberDone = sub -> sub.processedItems == sub.requestedItems;
    Predicate<Flow.Subscriber<?>> subscriberDone = sub -> mySubscriberDone.test((MySubscriber) sub);

    await()
        .atMost(Duration.ofSeconds(3))
        .until(() -> publisher.getSubscribers().stream().allMatch(subscriberDone));
    log.info("End");
  }

  @Data
  static class MySubscriber implements Flow.Subscriber<User> {

    final private String logCtx;
    final private int requestedItems;
    private int processedItems = 0;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
      subscription.request(requestedItems);
    }

    @Override
    public void onNext(User item) {
      log.info("{}:next :: {}", logCtx, item);
      processedItems++;
    }

    @Override
    public void onError(Throwable throwable) {
      log.info("{}:error :: {}", logCtx, throwable.getMessage(), throwable);
    }

    @Override
    public void onComplete() {
      log.info("{}:complete", logCtx);
    }
  }

}
