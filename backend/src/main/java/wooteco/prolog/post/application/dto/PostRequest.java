package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostRequest {

    private String title;
    private String content;
    private Long levelId;
    private Long missionId;
    private List<TagRequest> tags;
}