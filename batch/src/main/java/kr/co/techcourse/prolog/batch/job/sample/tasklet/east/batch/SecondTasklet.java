package kr.co.techcourse.prolog.batch.job.sample.tasklet.east.batch;

import kr.co.techcourse.prolog.batch.job.sample.tasklet.east.domain.Member;
import kr.co.techcourse.prolog.batch.job.sample.tasklet.east.service.MemberService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SecondTasklet implements Tasklet {

    private final MemberService memberService;

    private static int count;

    public SecondTasklet(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        ExecutionContext executionContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();
        Long memberId = executionContext.getLong("member");

        Member member = memberService.findMember(memberId);

        System.out.println(member.getNickname());

        if (++count >= 10) {
            count = 0;
            return RepeatStatus.FINISHED;
        }

        return RepeatStatus.CONTINUABLE;
    }
}
