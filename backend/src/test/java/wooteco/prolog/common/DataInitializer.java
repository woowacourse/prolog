package wooteco.prolog.common;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wooteco.support.fake.FakeDocumentRepository;

@Component
@Profile("test")
public class DataInitializer implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private List<FakeDocumentRepository> fakeDocumentRepositories;

    @Autowired
    private DataSource dataSource;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = new ArrayList<>();
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
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

    private void executeQueryWithTable(String tableName) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN "
            + "ID RESTART WITH 1").executeUpdate();
    }

    private void deleteAllDocuments() {
        fakeDocumentRepositories.forEach(it -> it.deleteAll());
    }
}
