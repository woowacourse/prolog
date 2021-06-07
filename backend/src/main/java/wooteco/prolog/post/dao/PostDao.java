package wooteco.prolog.post.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;
import wooteco.prolog.post.domain.Content;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.Title;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


/*
TODO 1. 멤버가 추가되면 하드 코딩된 AuthorResponse 제거
TODO 2. Mission
 */

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Post> postRowMapper =
            (rs, rowNum) -> {
                Member member = extractMember(rs);

                long id = rs.getLong("id");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updatedAt").toLocalDateTime();
                long missionId = rs.getLong("mission_id");
                String title = rs.getString("title");
                String content = rs.getString("content");

                return new Post(id,
                        member,
                        createdAt,
                        updatedAt,
                        new Title(title),
                        new Content(content),
                        missionId,
                        Collections.emptyList()
                );
            };

    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Member extractMember(ResultSet rs) throws SQLException {
        long memberId = rs.getLong("member_id");
        String nickname = rs.getString("nickname");
        Role role = Role.of(rs.getString("role"));
        Long githubId = rs.getLong("github_id");
        String imageUrl = rs.getString("image_url");

        return new Member(memberId, nickname, role, githubId, imageUrl);
    }

    public List<Post> findAll() {
        String query = "SELECT po.id as id, member_id, createdAt, updatedAt, title, content, mission_id, nickname, role, github_id, image_url FROM post AS po LEFT JOIN member AS me ON po.member_id = me.id";
        return this.jdbcTemplate.query(query, postRowMapper);
    }

    public List<Post> findWithFilter(List<Long> missions, List<Long> tags) {
        String query = "SELECT po.id as id, member_id, createdAt, updatedAt, title, content, mission_id, nickname, role, github_id, image_url, tag_id, post_id FROM post AS po LEFT JOIN member AS me ON po.member_id = me.id LEFT JOIN postTag AS pt ON po.id = pt.post_id WHERE 1=1";
        query += createDynamicColumnQuery("mission_id", missions);
        query += createDynamicColumnQuery("tag_id", tags);

        Object[] dynamicElements = Stream.concat(missions.stream(), tags.stream()).toArray();
        return this.jdbcTemplate.query(query, postRowMapper, dynamicElements);
    }

    private String createDynamicColumnQuery(String columnName, List<Long> columnIds) {
        if (columnIds.isEmpty()) {
            return "";
        }
        String missionDynamicQuery = " AND " + columnName + " IN (";
        String questionMarks = String.join(",", Collections.nCopies(columnIds.size(), "?"));
        missionDynamicQuery += questionMarks;
        missionDynamicQuery += ")";
        return missionDynamicQuery;
    }

    public Post insert(Post post) {
        String query = "INSERT INTO post (member_id, title, content, mission_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    query,
                    new String[]{"id"});
            pstmt.setLong(1, 1L); // TODO : MEMBER ID
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setLong(4, post.getMissionId());
            return pstmt;
        }, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return Post.of(id, post);
    }

    public void insert(List<Post> posts) {
        String query = "INSERT INTO post(member_id, title, content, mission_id) VALUES(?, ?, ?, ?)";

        this.jdbcTemplate.batchUpdate(query, posts, posts.size(), (pstmt, post) -> {
            pstmt.setLong(1, 1L);
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setLong(4, post.getMissionId());
        });
    }

    public Post findById(Long id) {
        String sql = "SELECT po.id as id, member_id, createdAt, updatedAt, title, content, mission_id, nickname, role, github_id, image_url FROM post AS po LEFT JOIN member AS me ON po.member_id = me.id WHERE po.id = ?";
        return this.jdbcTemplate.queryForObject(sql, postRowMapper, id);
    }
}
