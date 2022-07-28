package wooteco.prolog.studylog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentResponse {

    private Long id;
    private CommentMemberResponse author;
    private String content;
    private String createAt;

    public CommentResponse(Long id, CommentMemberResponse author, String content, String createAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createAt = createAt;
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            new CommentMemberResponse(
                comment.getMemberId(),
                comment.getMemberUsername(),
                comment.getMemberNickName(),
                comment.getMemberImageUrl(),
                comment.getMemberRole()),
            comment.getContent(),
            comment.getStringCreatedAt());
    }
}
