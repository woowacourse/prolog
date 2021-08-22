package wooteco.prolog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PrologApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrologApplication.class, args);
    }
}
