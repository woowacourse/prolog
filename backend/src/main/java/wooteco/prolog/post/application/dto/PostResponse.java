package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.dto.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MissionResponse mission;
    private String title;
    private String content;
    private List<TagResponse> tags;

    public PostResponse(Post post, MissionResponse missionResponse, List<TagResponse> tagResponses) {
        this(
                post.getId(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                missionResponse,
                post.getTitle(),
                post.getContent(),
                tagResponses);
    }

}