package wooteco.prolog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class RetryConfigTest {

    private static AtomicInteger count = new AtomicInteger(0);
    private static RuntimeException cause;

    @Autowired
    private TestService testService;

    @Test
    void retryTest() {
        testService.some();

        assertAll(
            () -> assertThat(count.get()).isEqualTo(4),
            () -> assertThat(cause).isNotNull()
        );
    }

    @EnableRetry
    @Configuration
    public static class TestConfig {

        @Bean
        public TestService testService() {
            return new TestService(testComponent());
        }

        @Bean
        public TestComponent testComponent() {
            return new TestComponent();
        }
    }

    public static class TestService {

        private final TestComponent testComponent;

        public TestService(final TestComponent testComponent) {
            this.testComponent = testComponent;
        }

        public void some() {
            testComponent.some();
        }
    }

    public static class TestComponent {

        @Retryable(
            retryFor = RuntimeException.class,
            backoff = @Backoff(delay = 1000),
            maxAttempts = 4,
            recover = "logging"
        )
        public void some() {
            count.incrementAndGet();
            throw new RuntimeException("ERROR!");
        }

        @Recover
        void logging(final RuntimeException e) {
            System.out.println("Recover!");
            cause = e;
        }
    }
}
