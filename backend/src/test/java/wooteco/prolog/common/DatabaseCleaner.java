package wooteco.prolog.common;

import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Profile("test")
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
            .filter(entityType -> Objects.nonNull(entityType.getJavaType().getAnnotation(Entity.class)))
            .map(entityType -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getName()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        tableNames.forEach(
            tableName -> executeQueryWithTable(tableName)
        );
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void executeQueryWithTable(String tableName) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN "
            + "ID RESTART WITH 1").executeUpdate();
    }
}
