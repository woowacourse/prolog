package wooteco.prolog.post.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.post.domain.Post;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarPostResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CalendarPostResponse of(Post post) {
        return new CalendarPostResponse(post.getId(), post.getTitle(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
