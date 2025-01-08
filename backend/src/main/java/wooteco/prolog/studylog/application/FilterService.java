package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.organization.application.OrganizationService;
import wooteco.prolog.organization.domain.OrganizationGroupSession;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.FilterResponse;

@Service
@AllArgsConstructor
public class FilterService {

    private final SessionService sessionService;
    private final MissionService missionService;
    private final OrganizationService organizationService;

    public FilterResponse showAll(LoginMember loginMember) {
        List<SessionResponse> sessionResponses = sessionService.findAllOrderByDesc();
        List<MissionResponse> missionResponses = missionService.findAllWithMyMissionFirst(loginMember);

        List<SessionResponse> mySessionResponses = sessionService.findMySessionResponses(loginMember);
        List<OrganizationGroupSession> organizationGroupSessions = organizationService.findOrganizationGroupSessionsByMemberId(
            loginMember.getId());
        List<SessionResponse> organizationSessions = organizationGroupSessions.stream()
            .map(it -> SessionResponse.of(it.getSession()))
            .collect(Collectors.toList());
        mySessionResponses.removeAll(organizationSessions);

        List<SessionResponse> mySessions = Stream.of(mySessionResponses, organizationSessions)
            .flatMap(Collection::stream)
            .sorted((s1, s2) -> Long.compare(s2.getId(), s1.getId()))
            .collect(toList());

        return new FilterResponse(sessionResponses, mySessions, missionResponses);
    }
}
