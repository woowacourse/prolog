package kr.co.techcourse.prolog.batch.job.sample.chunk.eve;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.sample.chunk.eve.entity.Number;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "eveChunkJob")
public class NumberChunkBatchJob implements InitializingBean {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;
    private final List<Integer> numbers;

    public NumberChunkBatchJob(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            EntityManagerFactory emf) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.emf = emf;
        numbers = new ArrayList<>();
    }

    @Bean
    public Job eveChunkJob() {
        return jobBuilderFactory.get("eveChunkJob")
                .start(saveNumber())
                .next(printNumber())
                .build();
    }

    @Bean
    public Step saveNumber() {
        return stepBuilderFactory.get("saveNumber")
                .<Integer, Number>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(numbers);
    }

    @Bean
    public ItemProcessor<Integer, Number> itemProcessor() {
        return Number::from;
    }

    @Bean
    public ItemWriter<Number> jpaItemWriter() {
        return new JpaItemWriterBuilder<Number>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
    }

    @Bean
    public Step printNumber() {
        return stepBuilderFactory.get("printNumber")
                .<Number, Number>chunk(10)
                .reader(jpaItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Number> jpaItemReader() {
        return new JpaPagingItemReaderBuilder<Number>()
                .name("jpaItemReader")
                .entityManagerFactory(emf)
                .pageSize(100)
                .queryString("SELECT n FROM Number n WHERE n.multipleOfThree = false")
                .build();
    }

    @Bean
    public ItemWriter<Number> itemWriter() {
        return new ItemWriter<>() {
            @Override
            public void write(List<? extends Number> items) {
                items.forEach(it -> System.out.println(it.getNumberValue()));
            }
        };
    }

    @Override
    public void afterPropertiesSet() {
        for (int i = 1; i <= 1000; i++) {
            numbers.add(i);
        }
    }
}
