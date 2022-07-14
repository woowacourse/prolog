package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs;

import kr.co.techcourse.prolog.batch.configuration.schedule.EnableBatchJob;
import kr.co.techcourse.prolog.batch.job.common.RunIdOnlyIncrementer;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.PopularStudylog;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.Studylog;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.step.delete.DeletePrevPopularStudylogsConfiguration;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.step.update.UpdatePopularStudylogsConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePopularStudylogsBatchConfiguration {

    public static final String JOB_NAME = "updatePopularStudylogs";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public UpdatePopularStudylogsBatchConfiguration(
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @EnableBatchJob(
        cron = "* * * * * MON-FRI",
        jobParameters = {"targetSize=100"}
    )
    @Bean(BEAN_PREFIX + "job")
    public Job job(
        @Qualifier(BEAN_PREFIX + "deletePrevPopularStudylogs") Step deletePrevPopularStudylogs,
        @Qualifier(BEAN_PREFIX + "updatePopularStudylogs") Step updatePopularStudylogs
    ) {
        return jobBuilderFactory.get(JOB_NAME)
            .incrementer(new RunIdOnlyIncrementer())
            .start(deletePrevPopularStudylogs)
            .next(updatePopularStudylogs)
            .build();
    }

    @Bean(BEAN_PREFIX + "deletePrevPopularStudylogs")
    public Step deletePrevPopularStudylogs(
        DeletePrevPopularStudylogsConfiguration configuration
    ) {
        return stepBuilderFactory.get("deletePrevPopularStudylogs")
            .<PopularStudylog, PopularStudylog>chunk(100)
            .reader(configuration.itemReader())
            .processor(configuration.processor())
            .writer(configuration.itemWriter())
            .build();
    }

    @JobScope
    @Bean(BEAN_PREFIX + "updatePopularStudylogs")
    public Step updatePopularStudylogs(
        UpdatePopularStudylogsConfiguration configuration,
        @Value("#{jobParameters[targetSize]}") int targetSize
    ) {
        return stepBuilderFactory.get("updatePopularStudylogs")
            .<Studylog, PopularStudylog>chunk(100)
            .reader(configuration.itemReader(targetSize))
            .processor(configuration.itemProcessor())
            .writer(configuration.itemWriter())
            .build();
    }

}
