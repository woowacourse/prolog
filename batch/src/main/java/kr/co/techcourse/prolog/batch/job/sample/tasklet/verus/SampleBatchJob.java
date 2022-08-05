package kr.co.techcourse.prolog.batch.job.sample.tasklet.verus;

import java.util.concurrent.atomic.AtomicInteger;
import kr.co.techcourse.prolog.batch.job.sample.tasklet.verus.member.Member;
import kr.co.techcourse.prolog.batch.job.sample.tasklet.verus.member.MemberRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "verusJob")
@Configuration
public class SampleBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public SampleBatchJob(JobBuilderFactory jobBuilderFactory,
                          StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job sampleJob(MemberRepository memberRepository) {
        return jobBuilderFactory.get("sampleJob")
                .start(step1(memberRepository))
                .next(step2(memberRepository))
                .build();
    }

    @Bean
    public Step step1(MemberRepository memberRepository) {
        return stepBuilderFactory.get("verus1")
                .tasklet((contribution, chunkContext) -> {
                    memberRepository.save(new Member("verus"));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2(MemberRepository memberRepository) {
        AtomicInteger counter = new AtomicInteger(0);

        return stepBuilderFactory.get("verus2")
                .tasklet((contribution, chunkContext) -> {
                    Member verus = memberRepository.findByName("verus");

                    System.out.println(verus.getName() + "#" + counter.get() + " hello!");

                    if (counter.addAndGet(1) == 10) {
                        counter.set(0);
                        return RepeatStatus.FINISHED;
                    }
                    return RepeatStatus.CONTINUABLE;
                })
                .build();
    }
}
