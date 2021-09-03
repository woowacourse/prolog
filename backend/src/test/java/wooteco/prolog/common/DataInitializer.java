package wooteco.prolog.common;

import com.google.common.base.CaseFormat;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("test")
public class DataInitializer implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestHighLevelClient esClient;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
            .filter(
                entityType -> Objects.nonNull(entityType.getJavaType().getAnnotation(Entity.class)))
            .map(entityType -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                entityType.getName()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        truncateAllTables();
        deleteAllDocuments();
    }

    private void truncateAllTables() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        tableNames.forEach(
            tableName -> executeQueryWithTable(tableName)
        );
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void deleteAllDocuments() {
        try {
            DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest("*");
            deleteRequest.setQuery(QueryBuilders.matchAllQuery());
            esClient.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void executeQueryWithTable(String tableName) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN "
            + "ID RESTART WITH 1").executeUpdate();
    }
}
