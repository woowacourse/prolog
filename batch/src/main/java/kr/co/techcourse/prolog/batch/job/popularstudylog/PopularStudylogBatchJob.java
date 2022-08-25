package kr.co.techcourse.prolog.batch.job.popularstudylog;

import kr.co.techcourse.prolog.batch.job.sample.chunk.entity.Crew;
import kr.co.techcourse.prolog.batch.job.sample.chunk.entity.Member;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "sampleChunkJob")
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
    public Job popularStudylogJob() {
        return jobBuilderFactory.get("popuarStudylogJob")
            .start(deleteOutdatedPopularStudylog())
            .next(updatePopularStudylog())
            .build();
    }

    @Bean
    public Step deleteOutdatedPopularStudylog() {
        return stepBuilderFactory.get("step1")
            .<Member, Crew>chunk(10)
            .reader()
            .processor()
            .writer()
            .build();
    }

    @Bean
    public Step updatePopularStudylog(

    ) {
        return null;
    }
}
