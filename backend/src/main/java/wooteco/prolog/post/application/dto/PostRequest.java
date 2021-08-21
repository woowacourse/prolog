package wooteco.prolog.post.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.dto.TagRequest;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostRequest {
    private String title;
    private String content;
    private Long missionId;
    private List<TagRequest> tags;
}