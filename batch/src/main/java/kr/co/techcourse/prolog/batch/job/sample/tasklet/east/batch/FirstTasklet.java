package kr.co.techcourse.prolog.batch.job.sample.tasklet.east.batch;

import kr.co.techcourse.prolog.batch.job.sample.tasklet.east.service.MemberService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class FirstTasklet implements Tasklet {

    private MemberService memberService;

    public FirstTasklet(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Long memberId = memberService.create("east");

        ExecutionContext stepExecutionContext = chunkContext.getStepContext()
                .getStepExecution()
                .getExecutionContext();
        stepExecutionContext.put("member", memberId);

        return RepeatStatus.FINISHED;
    }
}
