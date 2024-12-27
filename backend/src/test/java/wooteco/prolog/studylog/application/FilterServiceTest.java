package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.roadmap.application.dto.SessionResponse;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

@ExtendWith(MockitoExtension.class)
class FilterServiceTest {

    @InjectMocks
    FilterService filterService;

    @Mock
    SessionService sessionService;

    @Mock
    MissionService missionService;

    @Mock
    TagService tagService;

    @Mock
    MemberService memberService;

    @DisplayName("로그인 멤버와 관련된 세션, 미션, 태그, 멤버 정보를 가져와서 FilterResponse를 반환한다.")
    @Test
    void showAll() {
        // given
        LoginMember loginMember = new LoginMember();

        List<SessionResponse> sessionResponses = new ArrayList<>();
        sessionResponses.add(new SessionResponse(1L, "session1"));
        doReturn(sessionResponses).when(sessionService).findAllWithMySessionFirst(loginMember);

        List<MissionResponse> missionResponses = new ArrayList<>();
        missionResponses.add(new MissionResponse(1L, "mission1",
            new wooteco.prolog.session.application.dto.SessionResponse(1L, "session1")));
        doReturn(missionResponses).when(missionService).findAllWithMyMissionFirst(loginMember);

        List<TagResponse> tagResponses = new ArrayList<>();
        tagResponses.add(new TagResponse(1L, "tag1"));
        doReturn(tagResponses).when(tagService).findTagsIncludedInStudylogs();

        List<MemberResponse> memberResponses = new ArrayList<>();
        memberResponses.add(new MemberResponse(1L, "베베", "bebe", Role.CREW, "img"));
        doReturn(memberResponses).when(memberService).findAllOrderByNickNameAsc();

        // when
        FilterResponse response = filterService.showAll(loginMember);

        // then
        assertAll(
            () -> assertThat(response.getSessions()).isEqualTo(sessionResponses),
            () -> assertThat(response.getMissions()).isEqualTo(missionResponses)
        );
    }
}
