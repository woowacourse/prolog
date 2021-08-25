package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.studylog.domain.Post;
import wooteco.prolog.studylog.domain.PostTag;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponse {

    private Long id;
    private MemberResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MissionResponse mission;
    private String title;
    private String content;
    private List<TagResponse> tags;

    public PostResponse(
            Post post,
            MissionResponse missionResponse,
            List<TagResponse> tagResponses) {
        this(
                post.getId(),
                MemberResponse.of(post.getMember()),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                missionResponse,
                post.getTitle(),
                post.getContent(),
                tagResponses);
    }

    public static PostResponse of(Post post) {
        List<PostTag> postTags = post.getPostTags();
        List<TagResponse> tagResponses = toTagResponses(postTags);

        return new PostResponse(
                post.getId(),
                MemberResponse.of(post.getMember()),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                MissionResponse.of(post.getMission()),
                post.getTitle(),
                post.getContent(),
                tagResponses
        );
    }

    private static List<TagResponse> toTagResponses(List<PostTag> postTags) {
        return postTags.stream()
                .map(PostTag::getTag)
                .map(TagResponse::of)
                .collect(toList());
    }
}