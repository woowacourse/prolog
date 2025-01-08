package wooteco.prolog.studylog.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Comment;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentResponse {

    private Long id;
    private CommentMemberResponse author;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    public CommentResponse(Long id, CommentMemberResponse author, String content,
                           LocalDateTime localDateTime) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createAt = localDateTime;
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
