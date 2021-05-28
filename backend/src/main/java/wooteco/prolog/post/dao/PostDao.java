package wooteco.prolog.post.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.post.application.dto.AuthorResponse;
import wooteco.prolog.post.domain.Content;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.Title;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/*
TODO 1. 멤버가 추가되면 하드 코딩된 AuthorResponse 제거
TODO 2. Category
 */

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Post> postRowMapper =
            (rs, rowNum) -> {
                long id = rs.getLong("id");
                long memberId = rs.getLong("member_id");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updatedAt").toLocalDateTime();
                long categoryId = rs.getLong("category_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                return new Post(id,
                        new AuthorResponse(1L, "웨지", "https://i.ytimg.com/vi/3etKkkna-f0/hqdefault.jpg?sqp=-oaymwEjCPYBEIoBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLAhRhV8s0gUJe5yCOFctyEkGZFgTw"),
                        createdAt,
                        updatedAt,
                        new CategoryResponse(1L, "엄청나게 어려워서 머리 아픈 미션"),
                        new Title(title),
                        Arrays.asList("이미지", "진짜링크임", "들어가면 웨지사진 있음"),
                        new Content(content)
                );
            };

    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> findAll() {
        String query = "SELECT * FROM post";
        return this.jdbcTemplate.query(query, postRowMapper);
    }

    public Post insert(Post post) {
        String query = "INSERT INTO post(member_id, title, content, category_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    query,
                    new String[]{"id"});
            pstmt.setLong(1, 1L); // TODO : MEMBER ID
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setLong(4, post.getCategory().getId());
            return pstmt;
        }, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return Post.of(id, post);
    }

    public void insert(List<Post> posts) {
        String query = "INSERT INTO post(member_id, title, content, category_id) VALUES(?, ?, ?, ?)";

        this.jdbcTemplate.batchUpdate(query, posts, posts.size(), (pstmt, post) -> {
            pstmt.setLong(1, 1L);
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setLong(4, post.getCategory().getId());
        });
    }

    public Post findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql, postRowMapper, id);
    }
}
