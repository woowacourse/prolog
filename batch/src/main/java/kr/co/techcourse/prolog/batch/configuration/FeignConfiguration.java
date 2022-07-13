package kr.co.techcourse.prolog.batch.configuration;

import feign.FeignException;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static feign.Retryer.Default;

@EnableFeignClients(basePackages = "kr.co.techcourse.prolog")
@Configuration
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException exception = FeignException.errorStatus(methodKey, response);

            if (response.status() >= 500) {
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        exception,
                        null,
                        response.request()
                );
            }

            return exception;
        };
    }

    @Bean
    public Retryer retryer() {
        return new Default();
    }

}
