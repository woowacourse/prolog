package kr.co.techcourse.prolog.batch.configuration.schedule;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.MethodMetadata;
import org.springframework.stereotype.Component;

@Component
public class ScheduleBatchFinder {

    private final AnnotationConfigApplicationContext applicationContext;

    public ScheduleBatchFinder(ApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            throw new IllegalArgumentException(
                "[ScheduleBatchFinder] applicationContext must can be AnnotationConfigApplicationContext"
            );
        }

        this.applicationContext = (AnnotationConfigApplicationContext) applicationContext;
    }

    public List<BatchJob> findBatchJobs() {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(EnableBatchJob.class);

        return Arrays.stream(beanNames)
            .map(this::createBatchJob)
            .filter(Objects::nonNull)
            .collect(toUnmodifiableList());
    }

    @SuppressWarnings("ConstantConditions")
    private BatchJob createBatchJob(String beanName) {
        BeanDefinition beanDefinition = applicationContext.getBeanDefinition(beanName);
        Object bean = applicationContext.getBean(beanName);

        if (!(bean instanceof Job)) {
            return null;
        }

        validateBeanDefinition(beanDefinition);

        var methodMetaData = (MethodMetadata) beanDefinition.getSource();
        var annotationAttributes =
            methodMetaData.getAnnotationAttributes(EnableBatchJob.class.getName());

        return new BatchJob(
            beanName,
            (String) annotationAttributes.get("cron"),
            (String[]) annotationAttributes.get("jobParameters"),
            (Job) bean
        );
    }

    private void validateBeanDefinition(BeanDefinition beanDefinition) {
        if (!(beanDefinition instanceof AnnotatedBeanDefinition)) {
            throw new IllegalStateException(
                "[ScheduleBatchFinder] beanDefinition not an instance of AnnotatedBeanDefinition"
            );
        }
        if (!(beanDefinition.getSource() instanceof MethodMetadata)) {
            throw new IllegalStateException(
                "[ScheduleBatchFinder] beanDefinition.getSource() not an instance of MethodMetadata"
            );
        }
    }
}