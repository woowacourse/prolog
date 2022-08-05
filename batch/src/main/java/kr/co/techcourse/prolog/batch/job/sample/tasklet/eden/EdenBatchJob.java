package kr.co.techcourse.prolog.batch.job.sample.tasklet.eden;

import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.sample.tasklet.eden.application.MemberService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "edenJob")
public class EdenBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberService memberService;
    private final EntityManagerFactory emf;

    private int index;

    public EdenBatchJob(JobBuilderFactory jobBuilderFactory,
                        StepBuilderFactory stepBuilderFactory,
                        MemberService memberService,
                        EntityManagerFactory emf) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.memberService = memberService;
        this.emf = emf;
    }

    @Bean
    public Job edenJob() {
        return jobBuilderFactory.get("edenJob")
                .start(eden1())
                .next(eden2())
                .build();
    }

    @Bean
    public Step eden1() {
        return stepBuilderFactory.get("eden1")
                .tasklet(createMember("eden"))
                .build();
    }

    @Bean
    public Step eden2() {
        return stepBuilderFactory.get("eden2")
                .tasklet(getName("eden"))
                .build();
    }

    private Tasklet createMember(String name) {
        return (contribution, chunkContext) -> {
            memberService.createMember(name);
            return RepeatStatus.FINISHED;
        };
    }

    private Tasklet getName(String name) {
        return (contribution, chunkContext) -> {
            if (index < 10) {
                System.out.println(memberService.getMemberName(name));
                index++;
                return RepeatStatus.CONTINUABLE;
            }
            index = 0;
            return RepeatStatus.FINISHED;
        };
    }
}
