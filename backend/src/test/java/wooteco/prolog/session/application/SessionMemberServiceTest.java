package wooteco.prolog.session.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.GroupMemberService;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionMemberRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class SessionMemberServiceTest {

    @Autowired
    private SessionMemberService sessionMemberService;
    @Autowired
    private SessionRepository sessionRepository;
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
        final Session session = new Session("백엔드");
        final Session savedSession = sessionRepository.save(session);
        final Member savedBrown = memberService.findByUsername(brown.getLoginName());
        final SessionMember savedSessionMember = new SessionMember(savedSession.getId(), savedBrown);

        //when
        sessionMemberService.registerMember(savedSession.getId(), savedBrown.getId());
        //then
        final List<SessionMember> sessionMembers = sessionMemberService.findByMemberId(savedBrown.getId());

        assertAll(
                () -> assertThat(sessionMembers.stream()
                        .allMatch(it -> it.getMember().equals(savedBrown))).isTrue(),
                () -> assertThat(sessionMembers.stream()
                        .anyMatch(it -> it.getSessionId().equals(savedSessionMember.getSessionId()))).isTrue()
        );
    }
}
