package wooteco.prolog.member.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberTagService {

    private final MemberService memberService;
    private final StudylogService studylogService;

    public List<MemberTagResponse> findByMember(String memberName) {
        final Member member = memberService.findByUsername(memberName);
        final int postCount = studylogService.countPostByMember(member);
        return MemberTagResponse.asListFrom(member.getMemberTagsWithSort(), postCount);
    }
}
