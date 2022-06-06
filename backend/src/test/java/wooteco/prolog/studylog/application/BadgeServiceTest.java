package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.domain.BadgeType;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
public class BadgeServiceTest {

    @Autowired
    private StudylogService studylogService;
    @Autowired
    private BadgeService badgeService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StudylogLikeService likeService;

    private Member 브라운;
    private Member 토미;
    private Member 수달;
    private Member 베루스;

    private Session session1;
    private Session session2;

    @BeforeEach
    void setUp() {
        session1 = sessionRepository.save(new Session("세션1"));
        session2 = sessionRepository.save(new Session("세션2"));

        Mission 체스미션 = missionRepository.save(new Mission("체스미션", session1));
        Mission 지하철미션 = missionRepository.save(new Mission("지하철미션", session2));

        브라운 = memberRepository.save(new Member("brown", "브라운", Role.CREW, 1L, "imageUrl"));
        토미 = memberRepository.save(new Member("tommy", "토미", Role.CREW, 2L, "imageUrl"));
        수달 = memberRepository.save(new Member("sudal", "수달", Role.CREW, 3L, "imageUrl"));
        베루스 = memberRepository.save(new Member("verus", "베루스", Role.CREW, 4L, "imageUrl"));

        for (int i = 0; i < 3; i++) {
            Studylog studylog = studylogService.save(
                new Studylog(브라운, "체스 title" + i, "체스 content" + i, session1, 체스미션,
                    Collections.emptyList()));
            likeService.likeStudylog(수달.getId(), studylog.getId(), true);
            likeService.likeStudylog(베루스.getId(), studylog.getId(), true);
        }

        for (int i = 0; i < 4; i++) {
            Studylog studylog = studylogService.save(
                new Studylog(브라운, "지하철 title" + i, "지하철 content" + i, session2, 지하철미션,
                    Collections.emptyList()));
            likeService.likeStudylog(수달.getId(), studylog.getId(), true);
            likeService.likeStudylog(베루스.getId(), studylog.getId(), true);
        }

        for (int i = 0; i < 8; i++) {
            Studylog studylog = studylogService.save(
                new Studylog(베루스, "장바구니 title" + i, "장바구니 content" + i, session2, 지하철미션,
                    Collections.emptyList()));
            likeService.likeStudylog(수달.getId(), studylog.getId(), true);
            likeService.likeStudylog(베루스.getId(), studylog.getId(), true);
        }
    }

    @DisplayName("발급 받은 배지가 없는 사용자의 배지를 조회한다.")
    @Test
    void findEmptyBadge() {
        List<BadgeType> badges = badgeService.findBadges(토미.getId(),
            Arrays.asList(session1.getId(), session2.getId()));
        assertThat(badges).isEmpty();
    }

    @DisplayName("열정왕 배지를 발급 받은 사용자의 배지를 조회한다.")
    @Test
    void findPassionKingBadge() {
        List<BadgeType> badges = badgeService.findBadges(브라운.getId(),
            Arrays.asList(session1.getId(), session2.getId()));
        assertThat(badges).hasSize(1);
        assertThat(badges.get(0).toString()).isEqualTo(BadgeType.PASSION_KING.name());
    }

    @DisplayName("칭찬왕 배지를 받급받은 사용자의 배지를 조회한다.")
    @Test
    void findComplimentKingBadge() {
        List<BadgeType> badges = badgeService.findBadges(수달.getId(),
            Arrays.asList(session1.getId(), session2.getId()));
        assertThat(badges).hasSize(1);
        assertThat(badges.get(0).toString()).isEqualTo(BadgeType.COMPLIMENT_KING.name());
    }

    @DisplayName("칭찬왕과 열정왕 배지를 발급받은 사용자의 배지를 조회한다.")
    @Test
    void findAllBadges() {
        List<BadgeType> badges = badgeService.findBadges(베루스.getId(),
            Arrays.asList(session1.getId(), session2.getId()));
        assertThat(badges).hasSize(2);
        List<String> badgeNames = badges.stream()
            .map(BadgeType::toString)
            .collect(Collectors.toList());
        assertThat(badgeNames).containsExactlyInAnyOrder(BadgeType.PASSION_KING.name(),
            BadgeType.COMPLIMENT_KING.name());
    }
}
