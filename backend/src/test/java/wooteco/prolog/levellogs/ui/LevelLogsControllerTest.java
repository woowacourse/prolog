package wooteco.prolog.levellogs.ui;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import wooteco.prolog.levellogs.application.LevelLogService;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummariesResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummaryResponse;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.domain.repository.SelfDiscussionRepository;
import wooteco.prolog.levellogs.exception.InvalidLevelLogAuthorException;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
public class LevelLogsControllerTest {

    @Autowired
    private LevelLogRepository levelLogRepository;

    @Autowired
    private SelfDiscussionRepository selfDiscussionRepository;

    @Autowired
    private MemberRepository memberRepository;

    private LevelLogsController sut;

    @BeforeEach
    void setUp() {
        sut = new LevelLogsController(
            new LevelLogService(memberRepository, levelLogRepository, selfDiscussionRepository));
    }

    @Test
    @DisplayName("레벨 로그를 작성한다.")
    void createLevelLog() {
        // arrange
        Member author = memberRepository.save(new Member("sudal", "sudal", Role.CREW, 1L, "image"));

        final SelfDiscussionRequest selfDiscussionRequest1 = new SelfDiscussionRequest("질문2",
            "응답2");
        final SelfDiscussionRequest selfDiscussionRequest2 = new SelfDiscussionRequest("질문1",
            "응답1");
        final SelfDiscussionRequest selfDiscussionRequest3 = new SelfDiscussionRequest("질문3",
            "응답3");

        final LevelLogRequest levelLogRequest = new LevelLogRequest("제목", "내용",
            Arrays.asList(selfDiscussionRequest1, selfDiscussionRequest2, selfDiscussionRequest3));

        // act
        final ResponseEntity<Void> response = sut.create(
            new LoginMember(author.getId(), Authority.MEMBER),
            levelLogRequest);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    @DisplayName("레벨 로그를 수정한다.")
    void updateLevelLog() {
        // arrange
        Member author = memberRepository.save(new Member("verus", "verus", Role.CREW, 1L, "image"));
        LevelLog levelLog = levelLogRepository.save(new LevelLog("제목", "내용", author));
        selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문2", "응답2"));
        selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문1", "응답1"));
        selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문3", "응답3"));

        final SelfDiscussionRequest selfDiscussionRequest1 = new SelfDiscussionRequest("수정 질문2",
            "수정 응답2");
        final SelfDiscussionRequest selfDiscussionRequest2 = new SelfDiscussionRequest("수정 질문1",
            "수정 응답1");
        final SelfDiscussionRequest selfDiscussionRequest3 = new SelfDiscussionRequest("수정 질문3",
            "수정 응답3");

        final LevelLogRequest levelLogRequest = new LevelLogRequest("수정 제목", "수정 내용",
            Arrays.asList(selfDiscussionRequest1, selfDiscussionRequest2, selfDiscussionRequest3));

        // act
        final ResponseEntity<Void> response = sut.updateLovellog(
            new LoginMember(author.getId(), Authority.MEMBER), levelLog.getId(), levelLogRequest);

        final LevelLogResponse levelLogResponse = sut.findById(levelLog.getId()).getBody();

        // assert

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT),
            () -> assertThat(levelLogResponse.getContent()).isEqualTo(levelLogRequest.getContent()),
            () -> assertThat(levelLogResponse.getTitle()).isEqualTo(levelLogRequest.getTitle())
        );
    }

    @Test
    @DisplayName("레벨 로그 ID로 상세 정보를 조회한다.")
    void findByLevelLogId() {
        // arrange
        Member author = memberRepository.save(new Member("verus", "verus", Role.CREW, 1L, "image"));
        LevelLog levelLog = levelLogRepository.save(new LevelLog("제목", "내용", author));
        SelfDiscussion discussion2 = selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문2", "응답2"));
        SelfDiscussion discussion1 = selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문1", "응답1"));
        SelfDiscussion discussion3 = selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문3", "응답3"));

        // act
        ResponseEntity<LevelLogResponse> response = sut.findById(levelLog.getId());

        // assert
        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(OK),
            () -> assertThat(response.getBody()).isEqualTo(
                new LevelLogResponse(levelLog, Arrays.asList(discussion2, discussion1, discussion3))
            )
        );
    }

    @Test
    @DisplayName("존재하지 않는 레벨 로그 ID로 상세 정보 조회 시 예외를 반환한다.")
    void findByNotFoundLevelLogId() {
        assertThatThrownBy(() -> sut.findById(1L))
            .isInstanceOf(LevelLogNotFoundException.class);
    }

    @DisplayName("레벨 로그 목록을 조회한다.")
    @Test
    void findLevelLogs() {
        // arrange
        Member verus = memberRepository.save(
            new Member("verus", "verus", Role.CREW, 1L, "verus-image"));
        Member sudal = memberRepository.save(
            new Member("sudal", "sudal", Role.CREW, 2L, "sudal-image"));

        List<LevelLogSummaryResponse> verusLevelLogs = saveLevelLog(verus, 3);
        List<LevelLogSummaryResponse> sudalLevelLogs = saveLevelLog(sudal, 2);

        // act
        ResponseEntity<LevelLogSummariesResponse> response = sut
            .findAll(PageRequest.of(0, 3, Sort.by(Order.desc("createdAt"))));

        // assert
        List<LevelLogSummaryResponse> totalLevelLogs = new ArrayList<>(verusLevelLogs);
        totalLevelLogs.addAll(sudalLevelLogs);

        List<LevelLogSummaryResponse> expectedResponse = totalLevelLogs.stream()
            .sorted(comparing(LevelLogSummaryResponse::getCreatedAt).reversed())
            .collect(Collectors.toList());

        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(OK),
            () -> assertThat(response.getBody()).isEqualTo(
                new LevelLogSummariesResponse(expectedResponse.subList(0, 3), 5L, 2, 1)
            )
        );
    }

    private List<LevelLogSummaryResponse> saveLevelLog(Member member, int count) {
        List<LevelLogSummaryResponse> responses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            LevelLog levelLog = levelLogRepository.save(new LevelLog("제목 " + i, "내용 " + i, member));
            responses.add(new LevelLogSummaryResponse(levelLog));
        }
        return responses;
    }

    @DisplayName("본인이 작성한 레벨 로그를 삭제한다.")
    @Test
    void deleteLevelLog() {
        // arrange
        Member author = memberRepository.save(
            new Member("verus", "verus", Role.CREW, 1L, "verus-image"));
        LevelLog levelLog = levelLogRepository.save(new LevelLog("제목", "내용", author));
        SelfDiscussion discussion = selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문1", "응답1"));

        // act
        ResponseEntity<Void> response = sut
            .deleteById(new LoginMember(author.getId(), Authority.MEMBER), levelLog.getId());

        // assert
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
        assertThat(levelLogRepository.existsById(levelLog.getId())).isFalse();
        assertThat(selfDiscussionRepository.existsById(discussion.getId())).isFalse();
    }

    @DisplayName("존재하지 않는 레벨 로그 삭제 시 예외가 발생한다.")
    @Test
    void deleteNotFoundLevelLog() {
        // arrange
        Member author = memberRepository.save(
            new Member("verus", "verus", Role.CREW, 1L, "verus-image"));

        // act & assert
        assertThatThrownBy(
            () -> sut.deleteById(new LoginMember(author.getId(), Authority.MEMBER), 1L))
            .isInstanceOf(LevelLogNotFoundException.class);
    }

    @DisplayName("작성자 외의 사용자가 레벨 로그 삭제 시 예외가 발생한다.")
    @Test
    void deleteByAnotherMember() {
        // arrange
        Member another = memberRepository.save(
            new Member("sudal", "sudal", Role.CREW, 2L, "sudal-image"));

        Member author = memberRepository.save(
            new Member("verus", "verus", Role.CREW, 1L, "verus-image"));
        LevelLog levelLog = levelLogRepository.save(new LevelLog("제목", "내용", author));
        SelfDiscussion discussion = selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문1", "응답1"));

        // act & assert
        assertThatThrownBy(() -> sut.deleteById(new LoginMember(another.getId(), Authority.MEMBER),
            levelLog.getId()))
            .isInstanceOf(InvalidLevelLogAuthorException.class);
        assertThat(levelLogRepository.existsById(levelLog.getId())).isTrue();
        assertThat(selfDiscussionRepository.existsById(discussion.getId())).isTrue();
    }

    @DisplayName("존재하지 않는 사용자가 레벨 로그 삭제 시 예외가 발생한다.")
    @Test
    void deleteByNotFoundMember() {
        // arrange
        Member author = memberRepository.save(
            new Member("verus", "verus", Role.CREW, 1L, "verus-image"));
        LevelLog levelLog = levelLogRepository.save(new LevelLog("제목", "내용", author));
        SelfDiscussion discussion = selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, "질문1", "응답1"));

        // act & assert
        assertThatThrownBy(
            () -> sut.deleteById(new LoginMember(2L, Authority.MEMBER), levelLog.getId()))
            .isInstanceOf(MemberNotFoundException.class);
        assertThat(levelLogRepository.existsById(levelLog.getId())).isTrue();
        assertThat(selfDiscussionRepository.existsById(discussion.getId())).isTrue();
    }
}
