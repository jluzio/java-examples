package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.await;

public class ReactiveStreamsTest extends AbstractTest {
    @lombok.Data
    @AllArgsConstructor
    public class User {
        private String name;
        private String mail;
    }

    @Test
    public void test() {
        log.info("Reactive Streams");
        SubmissionPublisher<User> publisher = new SubmissionPublisher<>();

        publisher.subscribe(new MySubscriber("sub-1", 2));
        publisher.subscribe(new MySubscriber("sub-2", 1));

        IntStream.range(1, 10).forEach(
                v -> publisher.submit(new User("name-" + v, String.format("name-%s@example.org", v))));
        publisher.close();

        Predicate<MySubscriber> mySubscriberDone = sub -> sub.processedItems == sub.requestedItems;
        Predicate<Flow.Subscriber> subscriberDone = sub -> mySubscriberDone.test((MySubscriber) sub);

        await()
                .atMost(Duration.ofSeconds(3))
                .until(() -> publisher.getSubscribers().stream().allMatch(subscriberDone));
        log.info("End");
    }

    @Data
    class MySubscriber implements Flow.Subscriber<User> {
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
            log.info("{}:error :: {}", logCtx, throwable);
        }

        @Override
        public void onComplete() {
            log.info("{}:complete", logCtx);
        }
    }

}
