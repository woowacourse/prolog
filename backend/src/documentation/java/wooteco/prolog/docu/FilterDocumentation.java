package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.FilterService;
import wooteco.prolog.studylog.application.dto.FilterResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.ui.FilterController;

@WebMvcTest(controllers = FilterController.class)
public class FilterDocumentation extends NewDocumentation {

    @MockBean
    private FilterService filterService;

    @Test
    void 필터_목록_조회() {
        when(filterService.showAll(any())).thenReturn(FILTER_RESPONSE);

        given
            .when().get("/filters")
            .then().log().all().apply(document("filter/list")).statusCode(HttpStatus.OK.value());
    }

    private static final FilterResponse FILTER_RESPONSE = new FilterResponse(
        Lists.newArrayList(new SessionResponse(1L, "세션1"), new SessionResponse(2L, "세션2")),
        Lists.newArrayList(new MissionResponse(1L, "지하철 노선도 미션 1", new SessionResponse(1L, "세션1")), new MissionResponse(2L, "지하철 노선도 미션 2", new SessionResponse(2L, "세션2"))),
        Lists.newArrayList(new TagResponse(1L, "자바"), new TagResponse(2L, "파이썬"), new TagResponse(3L, "자바스크립트")),
        Lists.newArrayList(new MemberResponse(1L, "username", "nickname", Role.CREW, "imageUrl"))
    );
}
