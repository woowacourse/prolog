package wooteco.prolog.session.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.exception.SessionNotFoundException;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class SessionMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionMemberRepository sessionMemberRepository;

    @DisplayName("sessionId 와 member 에 일치하는 SessionMember 를 조회할 수 있다.")
    @Test
    void findSessionMemberBySessionIdAndMemberId() {
        // given
        Member 현구막 = 크루_생성("최현구", "현구막");
        Session 백엔드_레벨1 = 강의_생성("백엔드_레벨1");
        SessionMember 현구막_백엔드_레벨1 = 수강중인_강의_등록(현구막, 백엔드_레벨1);

        // when
        Optional<SessionMember> result = sessionMemberRepository.findBySessionIdAndMember(
            백엔드_레벨1.getId(),
            현구막
        );

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.orElseThrow(SessionNotFoundException::new)).isEqualTo(현구막_백엔드_레벨1);
    }

    private Session 강의_생성(String name) {
        return sessionRepository.save(new Session(name));
    }

    private Member 크루_생성(String username, String nickname) {
        return memberRepository.save(
            new Member(
                username,
                nickname,
                Role.NORMAL,
                9L,
                "기가막힌 URL"
            )
        );
    }

    private SessionMember 수강중인_강의_등록(Member member, Session session) {
        return sessionMemberRepository.save(new SessionMember(session.getId(), member));
    }
}
