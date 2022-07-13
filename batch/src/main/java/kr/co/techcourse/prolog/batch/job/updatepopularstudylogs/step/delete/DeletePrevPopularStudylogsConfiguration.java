package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.step.delete;

import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.PopularStudylog;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.stereotype.Component;

@Component
public class DeletePrevPopularStudylogsConfiguration {

    private final EntityManagerFactory emf;

    public DeletePrevPopularStudylogsConfiguration(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public JpaCursorItemReader<PopularStudylog> itemReader() {
        return new JpaCursorItemReaderBuilder<PopularStudylog>()
            .name("deletePrevPopularStudylogsItemReader")
            .entityManagerFactory(emf)
            .queryString("SELECT psl from PopularStudyLog psl where psl.deleted = false")
            .build();
    }

    public ItemProcessor<PopularStudylog, PopularStudylog> processor() {
        return item -> {
            item.delete();
            return item;
        };
    }

    public JpaItemWriter<PopularStudylog> itemWriter() {
        return new JpaItemWriterBuilder<PopularStudylog>()
            .entityManagerFactory(emf)
            .build();
    }
}
