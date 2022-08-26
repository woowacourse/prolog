package kr.co.techcourse.prolog.batch.job.popularstudylog;

import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.popularstudylog.delete.DeleteOutdatedPopularStudylogStepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "popuarStudylogJob")
public class PopularStudylogBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;

    public PopularStudylogBatchJob(
            JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory emf
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.emf = emf;
    }

    @Bean
    public Job popularStudylogJob() {
        return jobBuilderFactory.get("popuarStudylogJob")
                .start(deleteOutdatedPopularStudylogStep())
                .next(updatePopularStudylog())
                .build();
    }

    @Bean
    public Step deleteOutdatedPopularStudylogStep() {
        return new DeleteOutdatedPopularStudylogStepBuilder(emf, stepBuilderFactory).build();
    }

    @Bean
    public Step updatePopularStudylog() {
        return null;
    }
}
