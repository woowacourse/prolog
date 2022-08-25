package kr.co.techcourse.prolog.batch.job.popularstudylog.delete;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.PopularStudyLog;
import kr.co.techcourse.prolog.batch.job.sample.chunk.eve.entity.Number;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteOutdatedPopularStudylogConfiguration {

    private final EntityManagerFactory emf;

    public DeleteOutdatedPopularStudylogConfiguration(final EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Bean("outdatedPopularStudyLogReader")
    public JpaPagingItemReader<PopularStudyLog> itemReader() {
        return new JpaPagingItemReaderBuilder<PopularStudyLog>()
                .name("outdatedPopularStudyLogReader")
                .entityManagerFactory(emf)
                .pageSize(10)
                .queryString("select p from PopularStudyLog p where p.deleted = false")
                .build();
    }

    @Bean("outdatedPopularStudyLogProcessor")
    public ItemProcessor<PopularStudyLog, PopularStudyLog> itemProcessor() {
        return popularStudyLog -> {
            popularStudyLog.delete();
            return popularStudyLog;
        };
    }

    @Bean("deleteOutdatedPopularStudyLogWriter")
    public JpaItemWriter<PopularStudyLog> itemWriter() {
        return new JpaItemWriterBuilder<PopularStudyLog>()
            .entityManagerFactory(emf)
            .usePersist(false)
            .build();
    }
}
