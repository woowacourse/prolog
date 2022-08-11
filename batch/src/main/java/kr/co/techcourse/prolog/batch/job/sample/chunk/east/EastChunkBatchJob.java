package kr.co.techcourse.prolog.batch.job.sample.chunk.east;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.sample.chunk.east.entity.Numbers;
import kr.co.techcourse.prolog.batch.job.sample.chunk.east.entity.NumbersRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "eastChunkJob")
public class EastChunkBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NumbersRepository numbersRepository;
    private final EntityManagerFactory emf;
    private final List<Integer> numbers;

    public EastChunkBatchJob(JobBuilderFactory jobBuilderFactory,
                             StepBuilderFactory stepBuilderFactory,
                             NumbersRepository numbersRepository, EntityManagerFactory emf) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.numbersRepository = numbersRepository;
        this.emf = emf;
        this.numbers = IntStream.rangeClosed(1, 1000)
                .boxed()
                .collect(Collectors.toList());
    }

    @Bean
    public Job sampleChunkJob() {
        return jobBuilderFactory.get("eastChunkJob")
                .start(saveNumbers())
                .next(getAndPrintNumbers())
                .build();
    }

    @Bean
    public Step saveNumbers() {
        return stepBuilderFactory.get("saveNumbers")
                .<Integer, Numbers>chunk(20)
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
    public ItemProcessor<Integer, Numbers> itemProcessor() {
        return Numbers::from;
    }

    @Bean
    public ItemWriter<Numbers> jpaItemWriter() {
        return new JpaItemWriterBuilder<Numbers>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
    }

    private Step getAndPrintNumbers() {
        return stepBuilderFactory.get("getAndPrintNumbers")
                .<Numbers, Numbers>chunk(20)
                .reader(readNumbers())
                .writer(printNumbers())
                .build();
    }

    private ItemReader<Numbers> readNumbers() {
        return new JpaPagingItemReaderBuilder<Numbers>()
                .name("getNumbers")
                .pageSize(50)
                .entityManagerFactory(emf)
                .queryString("SELECT n FROM Numbers n WHERE n.multipleOfThree = false")
                .build();
    }

    private ItemWriter<Numbers> printNumbers() {
        return items -> {
            for (Numbers item : items) {
                System.out.println(item.getNumber());
            }
        };
    }
}
