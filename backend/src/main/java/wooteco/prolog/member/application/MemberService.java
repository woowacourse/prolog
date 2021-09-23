package wooteco.prolog.member.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.domain.GithubOAuth2User;
import wooteco.prolog.member.domain.LoginMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.support.security.oauth2.github.GithubProfileResponse;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private MemberRepository memberRepository;

    @Transactional
    public Member findOrCreateMember(GithubProfileResponse githubProfile) {
        return memberRepository.findByGithubId(githubProfile.getGithubId())
            .orElseGet(() -> memberRepository.save(toMember(githubProfile)));
    }

    @Transactional
    public Member findOrCreateMember(GithubOAuth2User githubProfile) {
        return memberRepository.findByGithubId(githubProfile.getGithubId())
            .orElseGet(() -> memberRepository.save(toMember(githubProfile)));
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
    public void updateMember(LoginMember member, MemberUpdateRequest updateRequest) {
        Member persistMember = findByUsername(member.getUsername());
        persistMember.updateImageUrl(updateRequest.getImageUrl());
        persistMember.updateNickname(updateRequest.getNickname());
    }

    public Member toMember(GithubProfileResponse profileResponse) {
        return new Member(
            profileResponse.getLoginName(),
            profileResponse.getNickname(),
            Role.CREW.name(),
            profileResponse.getGithubId(),
            profileResponse.getImageUrl()
        );
    }

    public Member toMember(GithubOAuth2User profileResponse) {
        return new Member(
            profileResponse.getLoginName(),
            profileResponse.getNickname(),
            Role.CREW.name(),
            profileResponse.getGithubId(),
            profileResponse.getImageUrl()
        );
    }
}
