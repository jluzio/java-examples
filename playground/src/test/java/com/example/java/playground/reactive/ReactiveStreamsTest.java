package com.example.java.playground.reactive;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

@SpringBootTest
public class ReactiveStreamsTest {
    private Logger log = LoggerFactory.getLogger(getClass());

    @lombok.Data
    @AllArgsConstructor
    public class User {
        private String name;
        private String mail;
    }

    @Test
    public void test() {
        SubmissionPublisher<User> publisher = new SubmissionPublisher<>();
        MySubscriber subscriber = new MySubscriber();

        publisher.subscribe(subscriber);

        IntStream.range(1, 10).forEach(
                v -> publisher.submit(new User("name-" + v, String.format("name-%s@example.org", v))));
    }

    class MySubscriber implements Flow.Subscriber<User> {
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscription.request(2);
        }

        @Override
        public void onNext(User item) {
            log.info("next: {}", item);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }

}
