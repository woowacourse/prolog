package wooteco.prolog.member.domain.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.member.domain.MemberTags;

@Component
@RequiredArgsConstructor
public class JdbcMemberTagRepository implements MemberTagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void register(MemberTags memberTags) {
        if (memberTags == null || memberTags.isEmpty()) {
            return;
        }

        addCount(memberTags.getValues());
        insertMemberTags(memberTags.getValues());
    }

    private void addCount(List<MemberTag> memberTags) {
        if (memberTags == null || memberTags.isEmpty()) return;

        String sql = "update member_tag set count = count + 1 where member_id = ? and tag_id in (%s)";
        executeInQuery(sql, memberTags);
    }

    private void insertMemberTags(List<MemberTag> memberTags) {
        if(memberTags == null || memberTags.isEmpty()) return;

        String sql = "insert into member_tag(member_id, tag_id, count) select ?,?,1 from dual where not exists (select * from member_tag where member_id = ? and tag_id = ? limit 1)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, memberTags.get(i).getMemberId());
                ps.setLong(2, memberTags.get(i).getTagId());
                ps.setLong(3, memberTags.get(i).getMemberId());
                ps.setLong(4, memberTags.get(i).getTagId());
            }

            @Override
            public int getBatchSize() {
                return memberTags.size();
            }
        });
    }

    @Override
    public void unregister(MemberTags memberTags) {
        if (memberTags == null || memberTags.isEmpty()) {
            return;
        }

        removeMemberTags(memberTags.getValues());
        removeCount(memberTags.getValues());
    }

    private void removeMemberTags(List<MemberTag> memberTags) {
        if (memberTags == null || memberTags.isEmpty()) return;

        String sql = "delete from member_tag where count = 1 and member_id = ? and tag_id in (%s)";
        executeInQuery(sql, memberTags);
    }

    private void removeCount(List<MemberTag> memberTags) {
        if (memberTags == null || memberTags.isEmpty()) return;

        String sql = "update member_tag set count = count - 1 where count > 1 and member_id = ? and tag_id in (%s)";
        executeInQuery(sql, memberTags);
    }

    private void executeInQuery(String sql, List<MemberTag> memberTags) {
        String inSql = String.join(",", Collections.nCopies(memberTags.size(), "?"));

        final String executeQuery = String.format(sql, inSql);

        List<Object> args = new ArrayList<>();
        args.add(memberTags.get(0).getMemberId());
        args.addAll(memberTags.stream().map(MemberTag::getTagId).collect(Collectors.toList()));

        jdbcTemplate.update(executeQuery, args.toArray());
    }


    @Override
    public void update(MemberTags originalMemberTags, MemberTags newMemberTags) {
        unregister(originalMemberTags);
        register(newMemberTags);
    }
}
