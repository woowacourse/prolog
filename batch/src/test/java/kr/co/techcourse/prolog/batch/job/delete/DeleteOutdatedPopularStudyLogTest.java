package kr.co.techcourse.prolog.batch.job.delete;

import static org.assertj.core.api.Assertions.assertThat;

import kr.co.techcourse.prolog.batch.job.popularstudylog.PopularStudylogBatchJob;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.PopularStudyLog;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.repository.PopularStudyLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PopularStudylogBatchJob.class)
public class DeleteOutdatedPopularStudyLogTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PopularStudyLogRepository popularStudyLogRepository;

    @Test
    public void updatePopularStudyLog() throws Exception {
        for (Long studyLogId = 1L; studyLogId <= 10L; studyLogId++) {
            popularStudyLogRepository.save(new PopularStudyLog(1L));
        }

        jobLauncherTestUtils.launchStep("deleteOutdatedPopularStudyLog");

        assertThat(popularStudyLogRepository.findAll()).isEmpty();
    }
}
