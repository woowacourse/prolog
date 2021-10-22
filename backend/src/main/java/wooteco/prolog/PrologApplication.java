package wooteco.prolog;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class PrologApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrologApplication.class, args);
    }

    @PostConstruct
    private void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
