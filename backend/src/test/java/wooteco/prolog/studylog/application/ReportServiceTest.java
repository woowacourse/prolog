package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import wooteco.prolog.common.fixture.ability.AbilityFixture;
import wooteco.prolog.common.fixture.level.LevelFixture;
import wooteco.prolog.common.fixture.member.MemberFixture;
import wooteco.prolog.common.fixture.misstion.MissionFixture;
import wooteco.prolog.common.fixture.studylog.StudylogFixture;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.report.ReportAssembler;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.studylog.domain.repository.LevelRepository;
import wooteco.prolog.studylog.domain.repository.MissionRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReportAssembler reportAssembler;

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private MissionRepository missionRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void createReport() throws IOException {
        Member member = createMember();
        setAbilities(member);
        setStudylogs(member);
        ReportRequest reportRequest = createRequest("jsons/report_post_request.json");
        ReportResponse reportResponse = reportService.createReport(reportRequest, member);

        assertThat(reportResponse)
            .usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(".*id", ".*updateAt", ".*createAt")
            .isEqualTo(expected("jsons/report_post_response.json"));
    }

    @Test
    void updateReport() throws IOException {
        Member member = createMember();
        setAbilities(member);
        setStudylogs(member);
        ReportRequest request = createRequest("jsons/report_post_request.json");
        reportService.createReport(request, member);

        ReportRequest updateRequest = createRequest("jsons/report_put_request.json");
        ReportResponse reportResponse = reportService.updateReport(updateRequest, member);

        assertThat(reportResponse)
            .usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(".*id", ".*updateAt", ".*createAt")
            .isEqualTo(expected("jsons/report_put_response.json"));

    }

    private ReportRequest createRequest(String source) throws IOException {
        String json = getJson(source);
        return objectMapper.readValue(json, ReportRequest.class);
    }

    private ReportResponse expected(String source) throws IOException {
        String json = getJson(source);

        return objectMapper.readValue(json, ReportResponse.class);
    }

    private String getJson(String source) throws IOException {
        String path = ClassLoader.getSystemResource(source).getPath();
        Path jsonPath = new File(path).toPath();
        return String.join(System.lineSeparator(), Files.readAllLines(jsonPath));
    }

    private Member createMember() {
        Member member = MemberFixture.crewMember1();
        return memberRepository.save(member);
    }

    private Studylog createStudyLog(Member member, Mission mission) {
        return StudylogFixture.builder()
            .mission(mission)
            .title("title")
            .content("content")
            .member(member)
            .build();
    }

    private void setAbilities(Member member) {
        abilityRepository.save(AbilityFixture.parentAbility1(member));
        abilityRepository.save(AbilityFixture.parentAbility2(member));
        abilityRepository.save(AbilityFixture.parentAbility3(member));
    }

    private void setStudylogs(Member member) {
        Level level1 = levelRepository.save(LevelFixture.level1());
        Mission mission = missionRepository.save(MissionFixture.mission1(level1));

        studylogRepository.save(createStudyLog(member, mission));
        studylogRepository.save(createStudyLog(member, mission));
        studylogRepository.save(createStudyLog(member, mission));
    }

    @Test
    void findReportsByMember() {
    }
}
