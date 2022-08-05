package kr.co.techcourse.prolog.batch.job.sample.tasklet.eve;

import kr.co.techcourse.prolog.batch.job.sample.tasklet.eve.domain.Member;
import kr.co.techcourse.prolog.batch.job.sample.tasklet.eve.domain.MemberRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "eveJob")
public class MemberBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberRepository memberRepository;
    private final StepDataShareBean<String> stepDataShareBean;
    private int count = 0;

    public MemberBatchJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                          MemberRepository memberRepository, StepDataShareBean<String> stepDataShareBean) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.memberRepository = memberRepository;
        this.stepDataShareBean = stepDataShareBean;
    }

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("eveJob")
                .start(eve1())
                .next(eve2())
                .build();
    }

    @Bean
    public Step eve1() {
        return stepBuilderFactory.get("eve1")
                .tasklet((contribution, chunkContext) -> {
                    String nickname = "eve";
                    stepDataShareBean.put("nickname", nickname);
                    memberRepository.save(new Member(nickname));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step eve2() {
        return stepBuilderFactory.get("eve2")
                .tasklet((contribution, chunkContext) -> {
                    Member member = memberRepository.findByNickname(stepDataShareBean.get("nickname"));
                    System.out.println(member.getNickname());
                    return RepeatStatus.continueIf(++count < 10);
                })
                .build();
    }
}
