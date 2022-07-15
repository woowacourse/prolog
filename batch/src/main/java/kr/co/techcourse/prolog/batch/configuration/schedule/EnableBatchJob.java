package kr.co.techcourse.prolog.batch.configuration.schedule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableBatchJob {

    /**
     * 배치를 실행하기 위한 cron expression 을 정의합니다.
     *
     * @return cron expression
     */
    String cron();

    /**
     * jobParameters 를 정의합니다. `@EnableBatchJob(jobParameters = {a=a, b=b})` 위와 같은 형식으로 정의합니다.
     * jobParameter의 용례는 spring batch의 용례를 그대로 사용합니다. (identifier 등..)
     */
    String[] jobParameters() default {};
}
