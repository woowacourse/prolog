package kr.co.techcourse.prolog.batch.job.delete;

import static org.assertj.core.api.Assertions.assertThat;

import kr.co.techcourse.prolog.batch.job.popularstudylog.delete.DeleteOutdatedPopularStudylogStepBuilder;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.PopularStudyLog;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.repository.PopularStudyLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(properties = "spring.batch.job.names=popuarStudylogJob")
public class DeleteOutdatedPopularStudyLogTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PopularStudyLogRepository popularStudyLogRepository;

    @Test
    void updatePopularStudyLog() {
        // arrange
        for (long id = 1L; id <= 10L; id++) {
            popularStudyLogRepository.save(new PopularStudyLog(id));
        }

        // act
        jobLauncherTestUtils.launchStep(DeleteOutdatedPopularStudylogStepBuilder.STEP_NAME);

        // assert
        assertThat(popularStudyLogRepository.findAll()).isEmpty();
    }
}
