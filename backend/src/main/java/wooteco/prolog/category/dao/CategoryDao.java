package wooteco.prolog.category.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.prolog.category.domain.Category;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Category insert(Category category) {
        String query = "INSERT INTO category(name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    query,
                    new String[]{"id"});
            pstmt.setString(1, category.getName());
            return pstmt;
        }, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);
        return new Category(id, category.getName());
    }

    public Optional<Category> findByName(String name) {
        String query = "SELECT * FROM category WHERE name = ?";
        try {
            Category category = jdbcTemplate.queryForObject(query, categoryRowMapper, name);
            return Optional.ofNullable(category);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Category> findById(Long id) {
        String query = "SELECT * FROM category WHERE id = ?";
        try {
            Category category = jdbcTemplate.queryForObject(query, categoryRowMapper, id);
            return Optional.ofNullable(category);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Category> findAll() {
        String query = "SELECT * FROM category";
        return jdbcTemplate.query(query, categoryRowMapper);
    }

    private static RowMapper<Category> categoryRowMapper =
            (rs, rowNum) -> {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                return new Category(id, name);
            };
}
