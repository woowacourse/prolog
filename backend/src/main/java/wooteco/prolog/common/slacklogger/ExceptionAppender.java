package wooteco.prolog.common.slacklogger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Profile({"prod", "dev"})
@AutoConfigurationPackage
public class ExceptionAppender {

    private static final String SLACK_ALARM_FORMAT = "[SlackAlarm] %s";

    private final RequestStorage requestStorage;
    private final SlackMessageGenerator slackMessageGenerator;
    private final PrologSlack prologSlack;

    public ExceptionAppender(RequestStorage requestStorage,
                             SlackMessageGenerator slackMessageGenerator,
                             PrologSlack prologSlack) {
        this.requestStorage = requestStorage;
        this.slackMessageGenerator = slackMessageGenerator;
        this.prologSlack = prologSlack;
    }

    @Before("@annotation(wooteco.prolog.common.slacklogger.SlackAlarm)")
    public void appendExceptionToResponseBody(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (!validateHasOneArgument(args)) {
            return;
        }

        if (!validateIsException(args)) {
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SlackAlarm annotation = signature.getMethod().getAnnotation(SlackAlarm.class);
        SlackAlarmErrorLevel level = annotation.level();

        String message = slackMessageGenerator
            .generate(requestStorage.get(), (Exception) args[0], level);
        prologSlack.send(message);
    }

    private boolean validateIsException(Object[] args) {
        if (!(args[0] instanceof Exception)) {
            log.warn("[SlackAlarm] argument is not Exception");
            return false;
        }

        return true;
    }

    private boolean validateHasOneArgument(Object[] args) {
        if (args.length != 1) {
            log.warn(String
                .format(SLACK_ALARM_FORMAT, "ambiguous exceptions! require just only one Exception"));
            return false;
        }

        return true;
    }
}

