package wooteco.prolog.studylog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentResponse {

    private Long id;
    private CommentMemberResponse member;
    private String content;
    private String createAt;

    public CommentResponse(Long id, CommentMemberResponse member, String content, String createAt) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.createAt = createAt;
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            new CommentMemberResponse(
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getMember().getNickname(),
                comment.getMember().getImageUrl(),
                comment.getMember().getRole().name()),
            comment.getContent(),
            comment.getCreatedAt().toString());
    }
}
