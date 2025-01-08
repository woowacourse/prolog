package wooteco.prolog.badge.application;

import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

import java.util.List;
import java.util.Optional;

public class ComplimentKingBadgeCreator implements BadgeCreator {

    private static final int COMPLIMENT_KING_CRITERIA = 15;

    private final BadgeRepository badgeRepository;
    private final List<Long> sessionIds;

    public ComplimentKingBadgeCreator(BadgeRepository badgeRepository, List<Long> sessionIds) {
        this.badgeRepository = badgeRepository;
        this.sessionIds = sessionIds;
    }

    @Override
    public Optional<BadgeType> create(String username) {
        int likeCount = badgeRepository.countLikesByUsernameDuringSessions(username, sessionIds);

        if (likeCount >= COMPLIMENT_KING_CRITERIA) {
            return Optional.of(BadgeType.COMPLIMENT_KING);
        }
        return Optional.empty();
    }
}
