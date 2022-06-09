package wooteco.prolog.studylog.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.BadgeType;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private static final int PASSION_KING_CRITERIA = 7;
    private static final int COMPLIMENT_KING_CRITERIA = 15;

    private final MemberService memberService;
    private final BadgeRepository badgeRepository;

    public List<BadgeType> findBadges(String username, List<Long> sessions) {
        Member member = memberService.findByUsername(username);

        int studylogCount = badgeRepository.countStudylogByUsernameDuringSessions(
            member.getUsername(),
            sessions);

        int likeCount = badgeRepository.countLikesByUsernameDuringSessions(
            member.getUsername(), sessions);

        return createBadges(studylogCount, likeCount);
    }

    private List<BadgeType> createBadges(int studylogCount, int likeCount) {
        List<BadgeType> badges = new ArrayList<>();

        if (isPassionKing(studylogCount)) {
            badges.add(BadgeType.PASSION_KING);
        }

        if (isComplimentKing(likeCount)) {
            badges.add(BadgeType.COMPLIMENT_KING);
        }

        return badges;
    }

    private boolean isPassionKing(int studylogCount) {
        return studylogCount >= PASSION_KING_CRITERIA;
    }

    private boolean isComplimentKing(int likeCount) {
        return likeCount >= COMPLIMENT_KING_CRITERIA;
    }
}
