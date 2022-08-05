package kr.co.techcourse.prolog.batch.job.sample.tasklet.east.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "eastSampleJob")
public class EastSampleBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Tasklet firstTasklet;
    private final Tasklet secondTasklet;

    public EastSampleBatchJob(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              Tasklet firstTasklet, Tasklet secondTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.firstTasklet = firstTasklet;
        this.secondTasklet = secondTasklet;
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener executionContextPromotionListener = new ExecutionContextPromotionListener();
        executionContextPromotionListener.setKeys(new String[]{"member"});
        return executionContextPromotionListener;
    }

    @Bean
    public Job eastSampleJob() {
        return jobBuilderFactory.get("eastSampleJob")
                .start(east1())
                .next(east2())
                .build();
    }

    @Bean
    public Step east1() {
        return stepBuilderFactory.get("east1")
                .tasklet(firstTasklet)
                .listener(promotionListener()).build();
    }

    @Bean
    public Step east2() {
        return stepBuilderFactory.get("east2")
                .tasklet(secondTasklet)
                .build();
    }
}
