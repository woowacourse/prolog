package kr.co.techcourse.prolog.batch.job.popularstudylog.update;

import javax.persistence.EntityManagerFactory;
import kr.co.techcourse.prolog.batch.job.popularstudylog.update.entity.PopularStudylog;
import kr.co.techcourse.prolog.batch.job.popularstudylog.update.entity.Studylog;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;

public class UpdatePopulartStudylogStepBuilder {

    private EntityManagerFactory emf;
    private StepBuilderFactory stepBuilderFactory;

    public UpdatePopulartStudylogStepBuilder(EntityManagerFactory emf,
                                             StepBuilderFactory stepBuilderFactory) {
        this.emf = emf;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    public Step build(){
        return stepBuilderFactory.get("updatePopularStudylog")
            .<Studylog, PopularStudylog> chunk(20)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
    }

    private JpaPagingItemReader<Studylog> itemReader() {
        String sql = "(select ss.id from \n"
            + "(select s.id, s.views from studylog s \n"
            + "join member m on s.member_id = m.id \n"
            + "join group_member gm on m.id = gm.member_id \n"
            + "join member_group mg on mg.id = gm.group_id \n"
            + "where mg.name like '%백엔드%' order by s.created_at limit 100) ss \n"
            + "group by ss.id \n"
            + "order by ( \n"
            + "select count(*) from likes l \n"
            + "where l.studylog_id = ss.id ) * 3 + ss.views desc \n"
            + "limit 10)\n"
            + "union all\n"
            + "(select ss.id from \n"
            + "(select s.id, s.views from studylog s \n"
            + "join member m on s.member_id = m.id \n"
            + "join group_member gm on m.id = gm.member_id \n"
            + "join member_group mg on mg.id = gm.group_id \n"
            + "where mg.name like '%프론트엔드%' order by s.created_at limit 100) ss \n"
            + "group by ss.id \n"
            + "order by ( \n"
            + "select count(*) from likes l \n"
            + "where l.studylog_id = ss.id ) * 3 + ss.views desc \n"
            + "limit 10)";
        JpaNativeQueryProvider<Studylog> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(sql);
        queryProvider.setEntityClass(Studylog.class);

        return new JpaPagingItemReaderBuilder<Studylog>()
            .name("findStudylogsByDays")
            .pageSize(20)
            .entityManagerFactory(emf)
            .queryProvider(queryProvider)
            .build();
    }

    private ItemProcessor<Studylog, PopularStudylog> itemProcessor() {
        return item -> new PopularStudylog(item.getId());
    }

    private JpaItemWriter<PopularStudylog> itemWriter() {
        return new JpaItemWriterBuilder<PopularStudylog>()
            .usePersist(true)
            .entityManagerFactory(emf)
            .build();
    }
}
