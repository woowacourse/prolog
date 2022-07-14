package kr.co.techcourse.prolog.batch.configuration.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.co.techcourse.prolog.batch.job.common.EnableBatchJob;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class ScheduleBatchFinderTest {

    @DisplayName("application context에서 Job과 메타 정보들을 가지고 온다.")
    @Test
    void findBatchJobs() {
        // given
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TestConfiguration_1.class, TestConfiguration_2.class);
        applicationContext.refresh();

        // when
        var scheduleBatchFinder = new ScheduleBatchFinder(applicationContext);
        List<BatchJob> batchJob = scheduleBatchFinder.findBatchJob();

        // then
        assertThat(batchJob)
            .usingRecursiveComparison()
            .ignoringFields("jobBean")
            .isEqualTo(List.of(
                new BatchJob(
                    "testcron",
                    new String[]{"a:a", "b:b"},
                    ':',
                    null
                ),
                new BatchJob(
                    "testcron",
                    new String[]{},
                    '=',
                    null
                )
            ));
    }

    @Configuration
    public static class TestConfiguration_1 {

        @Bean
        public Job test1() {
            return emptyJob();
        }

        @EnableBatchJob(cron = "testcron", jobParameters = {"a:a", "b:b"}, delimiter = ':')
        @Bean("testJob2")
        public Job test2() {
            return emptyJob();
        }

        @Bean("testJob3")
        public Job test3() {
            return emptyJob();
        }
    }

    @Configuration
    public static class TestConfiguration_2 {

        @EnableBatchJob(cron = "testcron")
        @Bean
        public Job test4() {
            return emptyJob();
        }

        @Bean("testJob5")
        public Job test5() {
            return emptyJob();
        }
    }

    public static Job emptyJob() {
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