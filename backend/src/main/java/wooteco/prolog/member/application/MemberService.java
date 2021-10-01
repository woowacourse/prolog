package wooteco.prolog.member.application;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.stream.Collectors;
>>>>>>> 865db151960ee58df685dc973a02785125394235
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private MemberRepository memberRepository;

    @Transactional
    public Member findOrCreateMember(GithubProfileResponse githubProfile) {
        return memberRepository.findByGithubId(githubProfile.getGithubId())
            .orElseGet(() -> memberRepository.save(githubProfile.toMember()));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);
    }

    public MemberResponse findMemberResponseByUsername(String username) {
        Member member = findByUsername(username);
        return MemberResponse.of(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequest updateRequest) {
        Member persistMember = findById(memberId);
        persistMember.updateImageUrl(updateRequest.getImageUrl());
        persistMember.updateNickname(updateRequest.getNickname());
    }

<<<<<<< HEAD
    public List<AbilityResponse> findMemberAbilities(Long memberId) {
        Member member = findById(memberId);
        return AbilityResponse.of(member.getAbilities());
=======
    public List<MemberResponse> findAll() {
        final List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberResponse::of)
            .collect(Collectors.toList());
>>>>>>> 865db151960ee58df685dc973a02785125394235
    }
}
