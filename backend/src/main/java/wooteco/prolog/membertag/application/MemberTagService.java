package wooteco.prolog.membertag.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.tag.dto.MemberTagResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberTagService {

    private final MemberService memberService;
    private final PostService postService;

    public List<MemberTagResponse> findByMember(String memberName) {
        final Member member = memberService.findByUsername(memberName);
        final int postCount = postService.countPostByMember(member);
        return MemberTagResponse.asListFrom(member.getMemberTagsWithSort(), postCount);
    }
}
