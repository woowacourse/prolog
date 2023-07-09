package wooteco.prolog.article.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;


@Component
public class AcceptanceTestExecutionListener extends AbstractTestExecutionListener {

    private JdbcTemplate jdbcTemplate;

    private AcceptanceTestExecutionListener() {
    }

    @Autowired
    public AcceptanceTestExecutionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeTestMethod(final TestContext testContext) {
        final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
        //final List<String> autoIncrementInit = getAutoIncrementInitQueries(jdbcTemplate);
        truncateTables(jdbcTemplate, truncateQueries);
    }


    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList("SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", String.class);
    }

    private List<String> getAutoIncrementInitQueries(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList("SELECT Concat('ALTER TABLE ', TABLE_NAME, 'AUTO_INCREMENT = 1;') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", String.class);
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("TRUNCATE article");
        jdbcTemplate.execute("TRUNCATE member");
        truncateQueries.forEach(v -> jdbcTemplate.execute(v));
        //autoIncrementInit.forEach(v -> jdbcTemplate.execute(v));
        jdbcTemplate.execute("ALTER TABLE article AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE member AUTO_INCREMENT = 1");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
