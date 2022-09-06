package wooteco.prolog.levellogs.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.domain.repository.SelfDiscussionRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class LevelLogServiceTest {

    @Autowired
    private LevelLogService levelLogService;

    @Autowired
    private LevelLogRepository levelLogRepository;

    @Autowired
    private SelfDiscussionRepository selfDiscussionRepository;

    @Autowired
    private MemberRepository memberRepository;

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

        final LevelLogResponse response = levelLogService.insertLevellogs(author.getId(),
            levelLogRequest);

        // act & assert
//        Assertions.assertDoesNotThrow(
//            () -> ;
    }

    @Test
    @DisplayName("레벨 로그를 수정한다.")
    void updateLevelLog() {
        // arrange
        Member author = memberRepository.save(new Member("sudal", "sudal", Role.CREW, 1L, "image"));
        final LevelLog levelLog = levelLogRepository.save(new LevelLog("title", "content", author));

        selfDiscussionRepository.save(new SelfDiscussion(levelLog, "질문1",
            "응답1"));
        selfDiscussionRepository.save(new SelfDiscussion(levelLog, "질문2",
            "응답2"));
        selfDiscussionRepository.save(new SelfDiscussion(levelLog, "질문3",
            "응답3"));

        final SelfDiscussionRequest selfDiscussionRequest1 = new SelfDiscussionRequest("수정질문1",
            "수정응답1");
        final SelfDiscussionRequest selfDiscussionRequest2 = new SelfDiscussionRequest("수정질문2",
            "수정응답2");
        final SelfDiscussionRequest selfDiscussionRequest3 = new SelfDiscussionRequest("수정질문3",
            "수정응답3");
        final LevelLogRequest updateRequest = new LevelLogRequest("제목수정1", "내용수정1",
            Arrays.asList(selfDiscussionRequest1, selfDiscussionRequest2, selfDiscussionRequest3));

        // act
        levelLogService.updateLevelLog(author.getId(), levelLog.getId(), updateRequest);

        //assert
        final LevelLog foundLevelLog = levelLogService.findById(levelLog.getId());

        assertAll(
            () -> assertThat(foundLevelLog.getContent()).isEqualTo(updateRequest.getContent()),
            () -> assertThat(foundLevelLog.getTitle()).isEqualTo(updateRequest.getTitle())
        );
    }
}
