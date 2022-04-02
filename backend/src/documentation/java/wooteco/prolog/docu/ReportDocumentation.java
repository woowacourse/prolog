package wooteco.prolog.docu;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.common.PageableResponse;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.ReportAbilityRequest;
import wooteco.prolog.report.application.dto.ReportAbilityResponse;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.application.dto.ReportStudylogResponse;
import wooteco.prolog.report.application.dto.ReportUpdateRequest;
import wooteco.prolog.report.ui.ReportController;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.TagResponse;

@WebMvcTest(controllers = ReportController.class)
public class ReportDocumentation extends NewDocumentation {

    @MockBean
    private ReportService reportService;

    @Test
    void 리포트_생성() {
        when(reportService.createReport(any(), any())).thenReturn(REPORT_RESPONSE);

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(REPORT_REQUEST)
            .when().post("/reports")
            .then().log().all().apply(document("reports/create")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 리포트_목록_조회() {
        PageableResponse response = new PageableResponse(REPORT_RESPONSES, 2L, 1, 1);
        when(reportService.findReportsByUsername(any(), any())).thenReturn(response);

        given
            .when().get("/members/username/reports")
            .then().log().all().apply(document("reports/list")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 리포트_조회() {
        when(reportService.findReportById(any())).thenReturn(REPORT_RESPONSE);

        given
            .when().get("/reports/1")
            .then().log().all().apply(document("reports/read")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량_수정() {
        doNothing().when(reportService).updateReport(any(), any(), any());

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .body(REPORT_UPDATE_REQUEST)
            .when().put("/reports/1")
            .then().log().all().apply(document("reports/update")).statusCode(HttpStatus.OK.value());
    }

    @Test
    void 역량_제거() {
        doNothing().when(reportService).deleteReport(any(), any());

        given.header(AUTHORIZATION, "Bearer " + accessToken)
            .when().delete("/reports/1")
            .then().log().all().apply(document("reports/delete")).statusCode(HttpStatus.NO_CONTENT.value());
    }

    private static final ReportRequest REPORT_REQUEST = new ReportRequest(
        "새로운 리포트",
        "새로운 리포트 설명",
        LocalDate.now().minusDays(14).toString(),
        LocalDate.now().toString(),
        Lists.newArrayList(
            new ReportAbilityRequest(1L, 5),
            new ReportAbilityRequest(2L, 8),
            new ReportAbilityRequest(3L, 2),
            new ReportAbilityRequest(4L, 3),
            new ReportAbilityRequest(5L, 2)
        )
    );

    private static final ReportUpdateRequest REPORT_UPDATE_REQUEST = new ReportUpdateRequest(
        "새로운 리포트",
        "새로운 리포트 설명"
    );

    private static final ReportResponse REPORT_RESPONSE = new ReportResponse(
        1L,
        "새로운 리포트",
        "새로운 리포트 설명",
        LocalDate.now().minusDays(14),
        LocalDate.now(),
        Lists.newArrayList(
            new ReportAbilityResponse(1L, "역량A", "역량A 설명", "#001122", 5, 54L),
            new ReportAbilityResponse(2L, "역량B", "역량B 설명", "#002122", 8, 57L),
            new ReportAbilityResponse(3L, "역량C", "역량C 설명", "#301172", 2, 52L),
            new ReportAbilityResponse(4L, "역량D", "역량D 설명", "#005122", 3, 32L),
            new ReportAbilityResponse(5L, "역량E", "역량E 설명", "#081142", 2, 24L)
        ),
        Lists.newArrayList(
            new ReportStudylogResponse(
                new StudylogResponse(
                    1L,
                    new MemberResponse(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    new MissionResponse(),
                    "제목",
                    "내용내용내용내용내용",
                    Lists.newArrayList(new TagResponse(1L, "태그1"), new TagResponse(2L, "태그2")),
                    false,
                    false,
                    10,
                    false,
                    1
                ),
                Lists.newArrayList(
                    new ReportAbilityResponse(1L, "역량A", "역량A 설명", "#001122", 5, 54L)
                )
            )
        )
    );

    private static final List<ReportResponse> REPORT_RESPONSES = Lists.newArrayList(
        new ReportResponse(
            1L,
            "새로운 리포트",
            "새로운 리포트 설명",
            LocalDate.now().minusDays(28),
            LocalDate.now().minusDays(14),
            Collections.emptyList(),
            Collections.emptyList()
        ),
        new ReportResponse(
            2L,
            "두번째 새로운 리포트",
            "두번째 새로운 리포트 설명",
            LocalDate.now().minusDays(14),
            LocalDate.now(),
            Collections.emptyList(),
            Collections.emptyList()
        )
    );
}
