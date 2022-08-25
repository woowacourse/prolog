package kr.co.techcourse.prolog.batch.job.popularstudylog;

import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.PopularStudyLog;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "updatePopularStudyLog")
public class PopularStudylogBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public PopularStudylogBatchJob(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job popularStudylogJob(
            @Qualifier("deleteOutdatedPopularStudyLogStep") Step deleteOutdatedPopularStudylogStep
    ) {
        return jobBuilderFactory.get("popuarStudylogJob")
                .start(deleteOutdatedPopularStudylogStep)
                .next(updatePopularStudylog())
                .build();
    }

    @Bean("deleteOutdatedPopularStudyLogStep")
    public Step deleteOutdatedPopularStudylog(
            @Qualifier("outdatedPopularStudyLogReader") JpaPagingItemReader<PopularStudyLog> itemReader,
            @Qualifier("outdatedPopularStudyLogProcessor") ItemProcessor<PopularStudyLog, PopularStudyLog> itemProcessor,
            @Qualifier("deleteOutdatedPopularStudyLogWriter") ItemWriter<PopularStudyLog> itemWriter
    ) {
        return stepBuilderFactory.get("deleteOutdatedPopularStudyLog")
                .<PopularStudyLog, PopularStudyLog>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step updatePopularStudylog(

    ) {
        return null;
    }
}
