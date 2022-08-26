package kr.co.techcourse.prolog.batch.job.popularstudylog.delete;

import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.popularstudylog.domain.PopularStudyLog;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;

public class DeleteOutdatedPopularStudylogStepBuilder {

    public static final String STEP_NAME = "deleteOutdatedPopularStudyLog";

    private final EntityManagerFactory emf;
    private final StepBuilderFactory stepBuilderFactory;

    public DeleteOutdatedPopularStudylogStepBuilder(final EntityManagerFactory emf,
                                                    final StepBuilderFactory stepBuilderFactory) {
        this.emf = emf;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    public Step build() {
        return stepBuilderFactory.get(STEP_NAME)
                .<PopularStudyLog, PopularStudyLog>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private JpaPagingItemReader<PopularStudyLog> itemReader() {
        return new JpaPagingItemReaderBuilder<PopularStudyLog>()
                .name("outdatedPopularStudyLogReader")
                .entityManagerFactory(emf)
                .queryString("select p from PopularStudyLog p where p.deleted = false")
                .build();
    }

    private ItemProcessor<PopularStudyLog, PopularStudyLog> itemProcessor() {
        return popularStudyLog -> {
            popularStudyLog.delete();
            return popularStudyLog;
        };
    }

    private JpaItemWriter<PopularStudyLog> itemWriter() {
        return new JpaItemWriterBuilder<PopularStudyLog>()
            .entityManagerFactory(emf)
            .build();
    }
}
