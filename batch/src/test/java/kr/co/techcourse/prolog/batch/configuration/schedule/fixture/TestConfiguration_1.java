package kr.co.techcourse.prolog.batch.configuration.schedule.fixture;

import kr.co.techcourse.prolog.batch.configuration.schedule.EnableBatchJob;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfiguration_1 {

    @Bean
    public Job test1() {
        return emptyJob();
    }

    @EnableBatchJob(cron = "* * * * * *", jobParameters = {"a:a", "b:b"})
    @Bean("testJob2")
    public Job test2() {
        return emptyJob();
    }

    @Bean("testJob3")
    public Job test3() {
        return emptyJob();
    }

    public Job emptyJob() {
        return new Job() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isRestartable() {
                return false;
            }

            @Override
            public void execute(JobExecution execution) {

            }

            @Override
            public JobParametersIncrementer getJobParametersIncrementer() {
                return null;
            }

            @Override
            public JobParametersValidator getJobParametersValidator() {
                return null;
            }
        };
    }
}