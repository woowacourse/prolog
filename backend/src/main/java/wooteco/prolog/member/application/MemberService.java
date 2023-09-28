package wooteco.prolog.member.application;

import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_FOUND;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.application.dto.MembersResponse;
import wooteco.prolog.member.application.dto.ProfileIntroRequest;
import wooteco.prolog.member.application.dto.ProfileIntroResponse;
import wooteco.prolog.member.application.dto.RoleUpdateRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;

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
            .orElseThrow(() -> new BadRequestException(MEMBER_NOT_FOUND));
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException(MEMBER_NOT_FOUND));
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
        loginMember.act().throwIfAnonymous(() -> new BadRequestException(MEMBER_NOT_ALLOWED));
        Member persistMember = loginMember.act().ifMember(memberId -> {
            Member member = findById(memberId);
            if (!Objects.equals(member.getUsername(), username)) {
                throw new BadRequestException(MEMBER_NOT_ALLOWED);
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
        loginMember.act().throwIfAnonymous(() -> new BadRequestException(MEMBER_NOT_ALLOWED));

        Member persistMember = loginMember.act().ifMember(memberId -> {
            Member member = findById(memberId);
            if (!Objects.equals(member.getUsername(), username)) {
                throw new BadRequestException(MEMBER_NOT_ALLOWED);
            }
            return member;
        }).getReturnValue(Member.class);

        persistMember.updateProfileIntro(updateRequest.getText());
    }

    public List<MemberResponse> findAllOrderByNickNameAsc() {
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

    @Transactional
    public void updateMemberRole(final LoginMember requestMember,
                                 final Long targetMemberId,
                                 final RoleUpdateRequest updateRequest) {
        requestMember.act().throwIfAnonymous(() -> new BadRequestException(MEMBER_NOT_ALLOWED));
        final Member member = findById(requestMember.getId());
        if (isMemberRoleLowerThanCoach(member)) {
            throw new BadRequestException(MEMBER_NOT_ALLOWED);
        }
        final Role newRole = Role.valueOf(updateRequest.getRole());
        updateMemberRole(targetMemberId, newRole);
    }

    private boolean isMemberRoleLowerThanCoach(final Member member) {
        return member.isGuest() || member.isCrew();
    }

    private void updateMemberRole(final Long memberId, final Role role) {
        final Member targetMember = findById(memberId);
        targetMember.updateRole(role);
    }
}
