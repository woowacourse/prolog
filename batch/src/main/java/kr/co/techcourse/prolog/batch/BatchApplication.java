package kr.co.techcourse.prolog.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing
@SpringBootApplication
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.REGEX, pattern = "kr.co.techcourse.prolog.batch.job.sample.tasklet.*")
})
@EnableJpaRepositories(excludeFilters = {
		@Filter(type = FilterType.REGEX, pattern = "kr.co.techcourse.prolog.batch.job.sample.tasklet.*")
})
@EntityScan(basePackages = "kr.co.techcourse.prolog.batch.job.sample.chunk.*")
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

}
