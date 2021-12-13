package wooteco.prolog.studylog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.member.util.MemberUtilCRUD;
import wooteco.prolog.studylog.application.StudylogLikeService;
import wooteco.prolog.studylog.application.dto.StudylogLikeResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.InvalidLikeRequestException;
import wooteco.prolog.studylog.exception.InvalidUnlikeRequestException;
import wooteco.prolog.studylog.studylog.util.StudylogFixture;
import wooteco.prolog.studylog.studylog.util.StudylogUtilCRUD;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
public class StudylogLikeServiceTest {

    @Autowired
    private StudylogLikeService studylogLikeService;

    @Autowired
    private StudylogRepository studylogRepository;

    @Autowired
    private MemberUtilCRUD memberUtilCRUD;

    @Autowired
    private StudylogUtilCRUD studylogUtilCRUD;

    private Member member;

    private Studylog studylog;

    @BeforeEach
    void setUp() {
        this.member = this.memberUtilCRUD.등록(MemberFixture.웨지);
        this.studylogUtilCRUD.등록(StudylogFixture.로또_미션_정리, MemberFixture.웨지);
        this.studylog = studylogRepository.findAll().get(0);
    }

    @DisplayName("로그인한 사용자가 스터디로그를 좋아요 한다 - 성공")
    @Test
    void like_loginMember_Success() {
        // given
        Long memberId = member.getId();
        Long studylogId = this.studylog.getId();
        boolean isMember = true;

        // when
        StudylogLikeResponse studylogLikeResponse = studylogLikeService
            .likeStudylog(memberId, studylogId, isMember);

        // then
        assertThat(studylogLikeResponse.isLiked()).isTrue();
        assertThat(studylogLikeResponse.getLikeCount()).isOne();
    }

    @DisplayName("비로그인 사용자가 스터디로그를 좋아요 한다 - 실패")
    @Test
    void like_Anonymous_InvalidLikeRequestException() {
        // given
        Long memberId = null;
        Long studylogId = this.studylog.getId();
        boolean isMember = false;

        // when
        // then
        assertThatThrownBy(() -> studylogLikeService
            .likeStudylog(memberId, studylogId, isMember))
            .isInstanceOf(InvalidLikeRequestException.class);
    }

    @DisplayName("이미 좋아요한 사용자가 스터디로그를 좋아요 한다 - 실패")
    @Test
    void like_DuplicatedLike_InvalidLikeRequestException() {
        // given
        Long memberId = member.getId();
        Long studylogId = this.studylog.getId();
        boolean isMember = true;

        studylogLikeService.likeStudylog(memberId, studylogId, isMember);

        // when
        // then
        assertThatThrownBy(() -> studylogLikeService
            .likeStudylog(memberId, studylogId, isMember))
            .isInstanceOf(InvalidLikeRequestException.class);
    }

    @DisplayName("로그인한 사용자가 스터디로그를 좋아요 취소 한다 - 성공")
    @Test
    void unlike_loginMember_Success() {
        // given
        Long memberId = member.getId();
        Long studylogId = this.studylog.getId();
        boolean isMember = true;

        studylogLikeService.likeStudylog(memberId, studylogId, isMember);

        // when
        StudylogLikeResponse studylogLikeResponse = studylogLikeService
            .unlikeStudylog(memberId, studylogId, isMember);

        // then
        assertThat(studylogLikeResponse.isLiked()).isFalse();
        assertThat(studylogLikeResponse.getLikeCount()).isZero();
    }

    @DisplayName("좋아요 하지 않은 사용자가 스터디로그를 좋아요 취소한다 - 실패")
    @Test
    void unlike_noPreviousLike_InvalidUnlikeRequestException() {
        // given
        Long memberId = member.getId();
        Long studylogId = this.studylog.getId();
        boolean isMember = true;

        // when
        // then
        assertThatThrownBy(() -> studylogLikeService
            .unlikeStudylog(memberId, studylogId, isMember))
            .isInstanceOf(InvalidUnlikeRequestException.class);
    }
}
