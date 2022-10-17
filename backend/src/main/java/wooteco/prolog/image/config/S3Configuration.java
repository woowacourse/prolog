package wooteco.prolog.image.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
