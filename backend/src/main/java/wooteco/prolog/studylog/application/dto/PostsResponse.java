package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import wooteco.prolog.studylog.domain.Post;
import wooteco.prolog.studylog.domain.PostTag;
import wooteco.prolog.studylog.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostsResponse {

    private static final int ONE_INDEXED_PARAMETER = 1;

    private List<PostResponse> data;
    private Long totalSize;
    private int totalPage;
    private int currPage;

    public static PostsResponse of(Page<Post> page) {
        Page<PostResponse> responsePage = page.map(PostsResponse::toResponse);
        return new PostsResponse(responsePage.getContent(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.getNumber() + ONE_INDEXED_PARAMETER);
    }

    private static PostResponse toResponse(Post post) {
        List<PostTag> postTags = post.getPostTags();
        final List<Tag> tags = postTags.stream()
                .map(PostTag::getTag)
                .collect(toList());

        return new PostResponse(post, MissionResponse.of(post.getMission()), toResponse(tags));
    }

    private static List<TagResponse> toResponse(List<Tag> tags) {
        return tags.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }

}
