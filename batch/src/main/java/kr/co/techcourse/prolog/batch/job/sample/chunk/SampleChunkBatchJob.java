package kr.co.techcourse.prolog.batch.job.sample.chunk;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.sample.chunk.entity.Crew;
import kr.co.techcourse.prolog.batch.job.sample.chunk.entity.Member;
import kr.co.techcourse.prolog.batch.job.sample.chunk.entity.MemberRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "sampleChunkJob")
public class SampleChunkBatchJob implements InitializingBean {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberRepository memberRepository;
    private final EntityManagerFactory emf;

    public SampleChunkBatchJob(
        JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        MemberRepository memberRepository,
        EntityManagerFactory emf
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.memberRepository = memberRepository;
        this.emf = emf;
    }

    @Bean
    public Job sampleChunkJob() {
        return jobBuilderFactory.get("sampleChunkJob")
            .start(step1())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<Member, Crew>chunk(10)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
    }

    @Bean
    public ListItemReader<Integer> itemReader() {
        List<Integer> collect = IntStream.rangeClosed(1, 1000)
            .boxed()
            .collect(toList());

        return new ListItemReader<>(collect);
    }

    @Bean
    public ItemProcessor<Member, Crew> itemProcessor() {
        return new ItemProcessor<Member, Crew>() {
            @Override
            public Crew process(Member item) throws Exception {
                return new Crew(null, item.getName());
            }
        };
    }

    @Bean
    public ItemWriter<Crew> itemWriter() {
        return new ItemWriter<Crew>() {
            @Override
            public void write(List<? extends Crew> items) throws Exception {
                items.forEach(item -> System.out.println(item.getName()));
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
