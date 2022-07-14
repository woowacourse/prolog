package kr.co.techcourse.prolog.batch.job.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableBatchJob {

    String cron();

    String[] jobParameters() default {};

    char delimiter() default '=';
}
