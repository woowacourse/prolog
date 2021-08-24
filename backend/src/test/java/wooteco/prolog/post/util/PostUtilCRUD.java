package wooteco.prolog.post.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.member.util.MemberUtilCRUD;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.dto.TagRequest;

@Component
public class PostUtilCRUD {

    @Autowired
    private PostService postService;
    @Autowired
    private MemberUtilCRUD memberUtilCRUD;

    public void 등록(PostFixture postFixture, MemberFixture member) {
        postService.insertPosts(memberUtilCRUD.등록(member), Collections.singletonList(postFixture.asRequest()));
    }

    public void 등록(MemberFixture memberFixture, String title, String content, Long missionId, String ... tagNames) {
        final List<TagRequest> tagRequests = Arrays.stream(tagNames)
                .map(TagRequest::new)
                .collect(Collectors.toList());

        postService.insertPosts(memberUtilCRUD.등록(memberFixture), Collections.singletonList(new PostRequest(title, content, missionId, tagRequests)));
    }

    public void 등록(PostFixture postFixture, MemberFixture memberFixture, String ... tagNames) {
        final List<TagRequest> tagRequests = Arrays.stream(tagNames)
                .map(TagRequest::new)
                .collect(Collectors.toList());

        postService.insertPosts(memberUtilCRUD.등록(memberFixture), Collections.singletonList(postFixture.asRequestWithTags(tagRequests)));
    }
}
