package wooteco.prolog.session.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.GroupMemberService;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionMemberRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class SessionMemberServiceTest {

    @Autowired
    private SessionMemberService sessionMemberService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private GroupMemberService groupMemberService;

    @Test
    void registerMember() {
        //given
        GithubProfileResponse brown = new GithubProfileResponse("브라운", "brown", "1",
                "image");
        memberService.findOrCreateMember(brown);
        final Member savedBrown = memberService.findById(1L);
        final SessionMember savedSessionMember = new SessionMember(1L, savedBrown);

        //when
        sessionMemberService.registerMember(1L, savedBrown.getId());
        //then
        final List<SessionMember> sessionMembers = sessionMemberService.findByMemberId(savedBrown.getId());
        final SessionMember foundSessionMember = sessionMembers.get(0);

        assertAll(
                () -> assertThat(foundSessionMember.getSessionId()).isEqualTo(savedSessionMember.getSessionId()),
                () -> assertThat(foundSessionMember.getMember()).isEqualTo(savedBrown)
        );
    }
}
