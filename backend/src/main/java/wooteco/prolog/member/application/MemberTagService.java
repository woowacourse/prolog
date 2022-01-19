package wooteco.prolog.member.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.member.domain.MemberTags;
import wooteco.prolog.member.domain.repository.MemberTagRepository;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberTagService {

    private final MemberService memberService;
    private final MemberTagRepository memberTagRepository;
    private final StudylogRepository studylogRepository;

    public List<MemberTagResponse> findByMember(String memberName) {
        final Member member = memberService.findByUsername(memberName);
        final int studylogCount = studylogRepository.countByMember(member);
        return MemberTagResponse.asListFrom(member.getMemberTagsWithSort(), studylogCount);
    }

    public void registerMemberTag(Tags tags, Member member) {
        final List<MemberTag> memberTags = tags.toMemberTags(member);
        memberTagRepository.register(new MemberTags(memberTags));
    }

    public void updateMemberTag(Tags originalTags, Tags newTags, Member member) {
        final List<MemberTag> originalMemberTags = originalTags.toMemberTags(member);
        final List<MemberTag> newMemberTags = newTags.toMemberTags(member);
        memberTagRepository.update(new MemberTags(originalMemberTags), new MemberTags(newMemberTags));
    }

    public void removeMemberTag(Tags tags, Member member) {
        final List<MemberTag> memberTags = tags.toMemberTags(member);
        memberTagRepository.unregister(new MemberTags(memberTags));
    }
}
