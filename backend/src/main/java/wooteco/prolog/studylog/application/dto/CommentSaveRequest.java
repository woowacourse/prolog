package wooteco.prolog.studylog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Comment;
import wooteco.prolog.studylog.domain.Studylog;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentSaveRequest {

    private Long memberId;
    private Long studylogId;
    private String content;

    public CommentSaveRequest(Long memberId, Long studylogId, String content) {
        this.memberId = memberId;
        this.studylogId = studylogId;
        this.content = content;
    }

    public Comment toEntity(Member member, Studylog studylog) {
        return new Comment(null, member, studylog, content);
    }
}
