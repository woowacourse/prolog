package wooteco.prolog.comment.application.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentResponse {

    private Long id;
    private CommentMemberResponse author;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponse(final Long id,
                           final CommentMemberResponse author,
                           final String content,
                           final LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
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
            comment.getCreatedAt());
    }
}
