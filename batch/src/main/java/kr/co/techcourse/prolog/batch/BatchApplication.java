package kr.co.techcourse.prolog.batch;

import kr.co.techcourse.prolog.batch.configuration.schedule.EnablePrologBatchSchedule;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnablePrologBatchSchedule
@EnableBatchProcessing
@EnableJpaRepositories
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
