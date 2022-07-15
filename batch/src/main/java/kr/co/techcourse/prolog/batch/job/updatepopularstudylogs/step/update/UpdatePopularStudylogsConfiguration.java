package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.step.update;

import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.PopularStudylog;
import kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog.Studylog;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePopularStudylogsConfiguration {

    private final EntityManagerFactory emf;

    public UpdatePopularStudylogsConfiguration(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public JpaCursorItemReader<Studylog> itemReader(int targetSize) {
        JpaNativeQueryProvider<Studylog> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery("SELECT * FROM studylog ORDER BY created_at desc");
        queryProvider.setEntityClass(Studylog.class);

        return new JpaCursorItemReaderBuilder<Studylog>()
            .name("updatePopularStudylogsItemReader")
            .entityManagerFactory(emf)
            .queryProvider(queryProvider)
            .maxItemCount(targetSize)
            .build();
    }

    public ItemProcessor<Studylog, PopularStudylog> itemProcessor() {
        return item -> new PopularStudylog(item.getId());
    }

    public JpaItemWriter<PopularStudylog> itemWriter() {
        return new JpaItemWriterBuilder<PopularStudylog>()
            .entityManagerFactory(emf)
            .usePersist(true)
            .build();
    }
}
