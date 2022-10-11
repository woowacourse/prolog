package wooteco.prolog.studylog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.prolog.common.fixture.misstion.MissionFixture.로또_미션;
import static wooteco.prolog.common.fixture.misstion.SessionFixture.임파시블_세션;
import static wooteco.prolog.member.util.MemberFixture.웨지;
import static wooteco.prolog.studylog.studylog.util.StudylogFixture.로또_미션_정리;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.StudylogLikeService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogLikeResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.exception.InvalidLikeRequestException;
import wooteco.prolog.studylog.exception.InvalidUnlikeRequestException;
import wooteco.prolog.studylog.studylog.util.StudylogFixture;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
@Transactional
public class StudylogLikeServiceTest {

    @Autowired
    private StudylogLikeService sut;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudylogService studylogService;

    @DisplayName("로그인한 사용자가 스터디로그를 좋아요 한다 - 성공")
    @Test
    void like_loginMember_Success() {
        // given
        long memberId = saveMember(웨지);
        long studylogId = saveStudyLog(memberId, 로또_미션_정리);
        boolean isMember = true;

        // when
        StudylogLikeResponse studylogLikeResponse = sut
            .likeStudylog(memberId, studylogId, isMember);

        // then
        assertThat(studylogLikeResponse.isLiked()).isTrue();
        assertThat(studylogLikeResponse.getLikesCount()).isOne();
    }

    @DisplayName("비로그인 사용자가 스터디로그를 좋아요 한다 - 실패")
    @Test
    void like_Anonymous_InvalidLikeRequestException() {
        // given
        long memberId = saveMember(웨지);
        long studylogId = saveStudyLog(memberId, 로또_미션_정리);
        Long anonymousMemberId = null;
        boolean isMember = false;

        // when
        // then
        assertThatThrownBy(() -> sut
            .likeStudylog(anonymousMemberId, studylogId, isMember))
            .isInstanceOf(InvalidLikeRequestException.class);
    }

    @DisplayName("이미 좋아요한 사용자가 스터디로그를 좋아요 한다 - 실패")
    @Test
    void like_DuplicatedLike_InvalidLikeRequestException() {
        // given
        long memberId = saveMember(웨지);
        long studylogId = saveStudyLog(memberId, 로또_미션_정리);
        boolean isMember = true;

        sut.likeStudylog(memberId, studylogId, isMember);

        // when
        // then
        assertThatThrownBy(() -> sut
            .likeStudylog(memberId, studylogId, isMember))
            .isInstanceOf(InvalidLikeRequestException.class);
    }

    @DisplayName("로그인한 사용자가 스터디로그를 좋아요 취소 한다 - 성공")
    @Test
    void unlike_loginMember_Success() {
        // given
        long memberId = saveMember(웨지);
        long studylogId = saveStudyLog(memberId, 로또_미션_정리);
        boolean isMember = true;

        sut.likeStudylog(memberId, studylogId, isMember);

        // when
        StudylogLikeResponse studylogLikeResponse = sut
            .unlikeStudylog(memberId, studylogId, isMember);

        // then
        assertThat(studylogLikeResponse.isLiked()).isFalse();
        assertThat(studylogLikeResponse.getLikesCount()).isZero();
    }

    @DisplayName("좋아요 하지 않은 사용자가 스터디로그를 좋아요 취소한다 - 실패")
    @Test
    void unlike_noPreviousLike_InvalidUnlikeRequestException() {
        // given
        long memberId = saveMember(웨지);
        long studylogId = saveStudyLog(memberId, 로또_미션_정리);
        boolean isMember = true;

        // when & then
        assertThatThrownBy(() -> sut
            .unlikeStudylog(memberId, studylogId, isMember))
            .isInstanceOf(InvalidUnlikeRequestException.class);
    }

    private Long saveMember(MemberFixture memberFixture) {
        return memberRepository.save(memberFixture.asDomain()).getId();
    }

    private Long saveStudyLog(Long memberId, StudylogFixture studylogFixture) {
        final SessionResponse sessionResponse = sessionService.create(임파시블_세션.asRequest());
        final MissionResponse missionResponse = missionService.create(로또_미션.asRequest(sessionResponse.getId()));
        final StudylogRequest studylogRequest = studylogFixture.asRequest(missionResponse.getId());
        return studylogService.insertStudylog(memberId, studylogRequest).getId();
    }
}
