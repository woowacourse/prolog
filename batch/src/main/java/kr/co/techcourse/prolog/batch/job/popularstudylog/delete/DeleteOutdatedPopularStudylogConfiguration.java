package kr.co.techcourse.prolog.batch.job.popularstudylog.delete;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteOutdatedPopularStudylogConfiguration {

    @Bean
    public JpaPagingItemReader<?> itemReader() {
        return new JpaPagingItemReaderBuilder()
            .build();
    }

    @Bean
    public ItemProcessor<?, ?> itemProcessor() {
        return new ItemProcessor<Object, Object>() {
            @Override
            public Object process(Object item) throws Exception {
                return null;
            }
        };
    }

    @Bean
    public JpaItemWriter<?> itemWriter() {
        return new JpaItemWriterBuilder()
            .build();
    }
}
