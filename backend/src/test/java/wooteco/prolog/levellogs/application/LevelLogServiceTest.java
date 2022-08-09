package wooteco.prolog.levellogs.application;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class LevelLogServiceTest {

    @Autowired
    private LevelLogService levelLogService;

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

        // act & assert
        Assertions.assertDoesNotThrow(
            () -> levelLogService.insertLevellogs(author.getId(), levelLogRequest));
    }
}
