package kr.co.techcourse.prolog.batch.configuration.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.co.techcourse.prolog.batch.configuration.schedule.fixture.TestConfiguration_1;
import kr.co.techcourse.prolog.batch.configuration.schedule.fixture.TestConfiguration_2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
                    "* * * * * *",
                    new String[]{"a:a", "b:b"},
                    ':',
                    null
                ),
                new BatchJob(
                    "* * * * * *",
                    new String[]{},
                    '=',
                    null
                )
            ));
    }
}