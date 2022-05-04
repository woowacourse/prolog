package wooteco.prolog.member.application;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.application.dto.ProfileIntroRequest;
import wooteco.prolog.member.application.dto.ProfileIntroResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotAllowedException;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.member.application.dto.MembersResponse;

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

    public ProfileIntroResponse findProfileIntro(String username) {
        Member member = findByUsername(username);
        return ProfileIntroResponse.of(member);
    }

    @Deprecated
    @Transactional
    public void updateMember_deprecated(Long memberId, MemberUpdateRequest updateRequest) {
        Member persistMember = findById(memberId);
        persistMember.updateImageUrl(updateRequest.getImageUrl());
        persistMember.updateNickname(updateRequest.getNickname());
    }

    @Transactional
    public void updateMember(LoginMember loginMember,
                             String username,
                             MemberUpdateRequest updateRequest) {
        loginMember.act().throwIfAnonymous(MemberNotAllowedException::new);
        Member persistMember = loginMember.act().ifMember(memberId -> {
            Member member = findById(memberId);
            if (!Objects.equals(member.getUsername(), username)) {
                throw new MemberNotAllowedException();
            }
            return member;
        }).getReturnValue(Member.class);

        persistMember.updateImageUrl(updateRequest.getImageUrl());
        persistMember.updateNickname(updateRequest.getNickname());
    }

    @Transactional
    public void updateProfileIntro(LoginMember loginMember,
                                   String username,
                                   ProfileIntroRequest updateRequest) {
        loginMember.act().throwIfAnonymous(MemberNotAllowedException::new);

        Member persistMember = loginMember.act().ifMember(memberId -> {
            Member member = findById(memberId);
            if (!Objects.equals(member.getUsername(), username)) {
                throw new MemberNotAllowedException();
            }
            return member;
        }).getReturnValue(Member.class);

        persistMember.updateProfileIntro(updateRequest.getText());
    }

    public List<MemberResponse> findAll() {
        final List<Member> members = memberRepository.findAll();
        return members.stream()
                .sorted(Comparator.comparing(Member::getNickname))
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }

    public MembersResponse findAll(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return MembersResponse.of(members);
    }

    public List<Member> findByIdIn(List<Long> memberIds) {
        return memberRepository.findByIdIn(memberIds);
    }
}
