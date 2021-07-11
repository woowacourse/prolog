package wooteco.prolog.post.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.post.application.dto.PageRequest;
import wooteco.prolog.post.domain.Content;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.post.domain.Direction;
import wooteco.prolog.post.domain.Title;
import wooteco.prolog.tag.domain.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;


/*
TODO 2. Mission
 */

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static ResultSetExtractor<List<Post>> postsResultSetExtractor = rs -> {
        List<Post> posts = new ArrayList<>();

        while (rs.next()) {
            Long id = rs.getLong("id");

            Post existPost = posts.stream()
                    .filter(it -> it.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (existPost == null) {
                Post post = extractPost(rs);
                posts.add(post);
            } else {
                Long tagId = rs.getLong("tag_id");
                existPost.addTadId(tagId);
            }
        }
        return posts;
    };

    private static ResultSetExtractor<Post> postResultSetExtractor = rs -> {
        Post post = null;

        while (rs.next()) {
            if (post == null) {
                post = extractPost(rs);
            } else {
                Long tagId = rs.getLong("tag_id");
                post.addTadId(tagId);
            }
        }
        return post;
    };

    private static Post extractPost(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        Long missionId = rs.getLong("mission_id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Long tagId = rs.getLong("tag_id");

        return new Post(id, extractMember(rs), createdAt, updatedAt, new Title(title), new Content(content), missionId, new ArrayList<>(Arrays.asList(tagId)));
    }

    private static Member extractMember(ResultSet rs) throws SQLException {
        long memberId = rs.getLong("member_id");
        String username = rs.getString("username");
        String nickname = rs.getString("nickname");
        Role role = Role.of(rs.getString("role"));
        Long githubId = rs.getLong("github_id");
        String imageUrl = rs.getString("image_url");

        return new Member(memberId, username, nickname, role, githubId, imageUrl);
    }

    private static Tag extractTag(ResultSet rs) throws SQLException {
        long tagId = rs.getLong("tag_id");
        String tagName = rs.getString("tag_name");

        return new Tag(tagId, tagName);
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM post";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Post> findAll() {
        String query = "SELECT po.id as id, member_id, created_at, updated_at, title, content, mission_id, nickname, username, role, github_id, image_url, tag.id as tag_id " +
                "FROM post AS po " +
                "LEFT JOIN member AS me ON po.member_id = me.id " +
                "LEFT JOIN post_tag AS pt ON po.id = pt.post_id " +
                "LEFT JOIN tag ON pt.tag_id = tag.id";
        return jdbcTemplate.query(query, postsResultSetExtractor);
    }

    public List<Post> findAllByMemberId(Long memberId) {
        String query = "SELECT po.id as id, member_id, created_at, updated_at, title, content, mission_id, nickname, username, role, github_id, image_url, tag.id as tag_id " +
                "FROM post AS po " +
                "LEFT JOIN member AS me ON po.member_id = me.id " +
                "LEFT JOIN post_tag AS pt ON po.id = pt.post_id " +
                "LEFT JOIN tag ON pt.tag_id = tag.id " +
                "WHERE po.member_id = " + memberId;
        return jdbcTemplate.query(query, postsResultSetExtractor);
    }

    public List<Post> findAllByUsername(String username) {
        String query = "SELECT po.id as id, member_id, created_at, updated_at, title, content, mission_id, nickname, username, role, github_id, image_url, tag.id as tag_id " +
                "FROM post AS po " +
                "LEFT JOIN member AS me ON po.member_id = me.id " +
                "LEFT JOIN post_tag AS pt ON po.id = pt.post_id " +
                "LEFT JOIN tag ON pt.tag_id = tag.id " +
                "WHERE me.username = ?";
        return jdbcTemplate.query(query, postsResultSetExtractor, username);
    }

    public List<Post> findWithFilter(List<Long> missions, List<Long> tags, PageRequest pageRequest) {
        String query = "SELECT po.id as id, member_id, created_at, updated_at, title, content, mission_id, nickname, username, role, github_id, image_url, tag.id as tag_id " +
                "FROM (SELECT * FROM post" +
                createPagingQuery(pageRequest.getSize(), pageRequest.getPage()) +
                ") AS po " +
                "LEFT JOIN member AS me ON po.member_id = me.id " +
                "LEFT JOIN post_tag AS pt ON po.id = pt.post_id " +
                "LEFT JOIN tag ON pt.tag_id = tag.id " +
                "WHERE 1=1";
        query += createDynamicColumnQuery("mission_id", missions);
        query += createDynamicColumnQuery("tag_id", tags);

        query += createSortQuery(pageRequest.getDirection());
        Object[] dynamicElements = Stream.concat(missions.stream(), tags.stream()).toArray();

        return jdbcTemplate.query(query, postsResultSetExtractor, dynamicElements);
    }

    public Post insert(Post post) {
        String query = "INSERT INTO post (member_id, title, content, mission_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    query,
                    new String[]{"id"});
            pstmt.setLong(1, post.getMember().getId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setLong(4, post.getMissionId());
            return pstmt;
        }, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return Post.of(id, post);
    }

    public void insert(List<Post> posts) {
        String query = "INSERT INTO post(member_id, title, content, mission_id) VALUES(?, ?, ?, ?)";

        this.jdbcTemplate.batchUpdate(query, posts, posts.size(), (pstmt, post) -> {
            pstmt.setLong(1, post.getMember().getId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setLong(4, post.getMissionId());
        });
    }

    public Post findById(Long id) {
        String sql = "SELECT po.id as id, member_id, created_at, updated_at, title, content, mission_id, nickname, username, role, github_id, image_url, tag.id as tag_id " +
                "FROM post AS po LEFT JOIN member AS me ON po.member_id = me.id " +
                "LEFT JOIN post_tag AS pt ON po.id = pt.post_id " +
                "LEFT JOIN tag ON pt.tag_id = tag.id " +
                "WHERE po.id = ?";
        return jdbcTemplate.query(sql, postResultSetExtractor, id);
    }

    public void update(Long id, Post updatedPost) {
        String query = "UPDATE post SET title = ?, content = ?, mission_id = ? WHERE id = ?";

        this.jdbcTemplate.update(
                query,
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getMissionId(), id
        );
    }
    public void deleteById(Long id) {
        String sql = "DELETE FROM post WHERE post.id = ?";
        jdbcTemplate.update(sql, id);
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

    private String createSortQuery(Direction direction) {
        String orderByQuery = " ORDER BY id ";
        orderByQuery += direction.name(); // DESC or ASC
        return orderByQuery;
    }

    private String createPagingQuery(int size, int page) {
        if (page > 0) {
            page -= 1;
        }
        return " LIMIT " + page * size + " , " + size;
    }
}
