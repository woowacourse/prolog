package wooteco.prolog.docu;

import static java.util.stream.Collectors.toList;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import wooteco.prolog.DataLoaderApplicationListener;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.report.application.AbilityService;
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.report.request.studylog.ReportStudylogRequest;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;
import wooteco.prolog.report.application.dto.report.response.ability_graph.GraphResponse;
import wooteco.prolog.report.application.dto.report.response.studylogs.StudylogAbilityResponse;
import wooteco.prolog.report.application.dto.report.response.studylogs.StudylogResponse;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.LevelService;
import wooteco.prolog.studylog.application.MissionService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.update.UpdatedContentsRepository;

class ReportDocumentation extends Documentation {

    private final LevelService levelService;
    private final MissionService missionService;
    private final TagService tagService;
    private final MemberService memberService;
    private final StudylogService studylogService;
    private final DocumentService studyLogDocumentService;
    private final AbilityService abilityService;
    private final UpdatedContentsRepository updatedContentsRepository;
    private final ReportService reportService;
    private final ApplicationContext applicationContext;

    private static boolean flag = false;

    @Autowired
    public ReportDocumentation(LevelService levelService,
                               MissionService missionService,
                               TagService tagService,
                               MemberService memberService,
                               StudylogService studylogService,
                               DocumentService studyLogDocumentService,
                               AbilityService abilityService,
                               UpdatedContentsRepository updatedContentsRepository,
                               ReportService reportService,
                               ApplicationContext applicationContext) {
        this.levelService = levelService;
        this.missionService = missionService;
        this.tagService = tagService;
        this.memberService = memberService;
        this.studylogService = studylogService;
        this.studyLogDocumentService = studyLogDocumentService;
        this.abilityService = abilityService;
        this.updatedContentsRepository = updatedContentsRepository;
        this.reportService = reportService;
        this.applicationContext = applicationContext;
    }

    @Override
    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build();

        if(!flag) {
            DataLoaderApplicationListener dataLoaderApplicationListener = new DataLoaderApplicationListener(
                levelService,
                missionService,
                tagService,
                memberService,
                studylogService,
                studyLogDocumentService,
                abilityService,
                updatedContentsRepository,
                reportService
            );
            dataLoaderApplicationListener.onApplicationEvent(new ContextRefreshedEvent(applicationContext));
            flag = true;
        }
    }

    private String 로그인(GithubResponses githubResponse) {
        return RestAssured.given().log().all()
            .body(new TokenRequest(githubResponse.getCode()))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/login/token")
            .then().log().all()
            .extract().body().as(TokenResponse.class)
            .getAccessToken();
    }

    private ReportResponse 리포트조회(Long id) {
        return RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .when()
            .get("/reports/{reportId}", id)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(ReportResponse.class);
    }

    @Test
    void 멤버의리포트를조회한다_simple() {
        given("reports/read/members/simple")
            .contentType(ContentType.JSON)
            .when()
            .get("/reports?username={username}&type=simple&page=0&size=10",  "devhyun637")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    @Test
    void 멤버의리포트를조회한다_all() {
        given("reports/read/members/all")
            .contentType(ContentType.JSON)
            .when()
            .get("/reports?username={username}&type=all&page=0&size=10",  "devhyun637")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    @Test
    void 레포트를조회한다() {
        given("reports/read/once")
            .contentType(ContentType.JSON)
            .when()
            .get("/reports/{reportId}", 1)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    @Test
    void 레포트를업데이트한다() {
        final ReportResponse reportResponse = 리포트조회(1L);
        final ReportRequest changedReport = 리포트변경(reportResponse);
        given("reports/update")
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인(GithubResponses.티케))
            .body(changedReport)
            .when()
            .put("/reports/{reportId}", 1)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    private ReportRequest 리포트변경(ReportResponse reportResponse) {
        return new ReportRequest(
            reportResponse.getId(),
            "변역된 타이틀",
            "변경된 설명",
            toRequest(reportResponse.getAbilityGraph()),
            toRequest(reportResponse.getStudylogs()),
            reportResponse.isRepresent()
        );
    }

    private GraphRequest toRequest(GraphResponse abilityGraph) {
        final List<AbilityRequest> abilityRequests = abilityGraph.getAbilities().stream()
            .map(ability -> new AbilityRequest(
                ability.getId(),
                ability.getWeight(),
                ability.isPresent())
            ).collect(toList());

        return new GraphRequest(abilityRequests);
    }

    private List<ReportStudylogRequest> toRequest(List<StudylogResponse> studylogResponses) {
        return studylogResponses.stream()
            .map(studylogResponse -> new ReportStudylogRequest(
                studylogResponse.getId(),
                toRequest(studylogResponse))
            ).collect(toList());
    }

    private List<Long> toRequest(StudylogResponse studylogResponse) {
        return studylogResponse.getAbilities().stream().map(StudylogAbilityResponse::getId)
            .collect(toList());
    }

    @Test
    void 리포트를생성한다() {
        ReportResponse reportResponse = 리포트조회(1L);
        String accessToken = 로그인(GithubResponses.티케);
        final ReportRequest reportRequest = toNewReportRequest(reportResponse);
        given("reports/create")
            .contentType(ContentType.JSON)
            .body(reportRequest)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .post("reports")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    @Test
    void 리포트를삭제한다() {
        String accessToken = 로그인(GithubResponses.티케);
        given("reports/delete")
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when()
            .delete("reports/{reportId}", 2)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    private ReportRequest toNewReportRequest(ReportResponse reportResponse) {
        return new ReportRequest(
            null,
            "새로운 리포트",
            reportResponse.getDescription(),
            toRequest(reportResponse.getAbilityGraph()),
            toRequest(reportResponse.getStudylogs()),
            reportResponse.isRepresent()
        );
    }
}
