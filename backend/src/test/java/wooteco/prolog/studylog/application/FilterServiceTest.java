package wooteco.prolog.studylog.application;

import de.flapdoodle.embed.process.collections.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.organization.application.OrganizationService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FilterServiceTest {

    @InjectMocks
    FilterService filterService;

    @Mock
    SessionService sessionService;

    @Mock
    MissionService missionService;

    @Mock
    OrganizationService organizationService;

    @DisplayName("로그인 멤버와 관련된 FilterResponse를 반환한다.")
    @Test
    void showAll() {
        // given
        LoginMember loginMember = new LoginMember(1L, Authority.MEMBER);

        SessionResponse session1 = new SessionResponse(1L, "session1");
        List<SessionResponse> sessionResponses = Collections.newArrayList(session1);
        doReturn(sessionResponses).when(sessionService).findAllOrderByDesc();

        List<MissionResponse> missionResponses = Collections.newArrayList(
            new MissionResponse(1L, "mission1", session1));
        doReturn(missionResponses).when(missionService).findAllWithMyMissionFirst(loginMember);

        SessionResponse session2 = new SessionResponse(2L, "session2");
        List<SessionResponse> mySessionResponses = Collections.newArrayList(session2);
        doReturn(mySessionResponses).when(sessionService).findMySessions(loginMember);

        // when
        FilterResponse filterResponse = filterService.showAll(loginMember);

        // then
        assertAll(
            () -> assertThat(filterResponse.getSessions()).isEqualTo(sessionResponses),
            () -> assertThat(filterResponse.getMySessions()).containsExactlyInAnyOrderElementsOf(mySessionResponses),
            () -> assertThat(filterResponse.getMissions()).isEqualTo(missionResponses)
        );
    }
}
