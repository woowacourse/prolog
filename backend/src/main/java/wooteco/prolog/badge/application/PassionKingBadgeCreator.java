package wooteco.prolog.badge.application;

import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

import java.util.List;
import java.util.Optional;

public class PassionKingBadgeCreator implements BadgeCreator {

    private static final int PASSION_KING_CRITERIA = 7;

    private final BadgeRepository badgeRepository;
    private final List<Long> sessions;


    public PassionKingBadgeCreator(BadgeRepository badgeRepository, List<Long> sessions) {
        this.badgeRepository = badgeRepository;
        this.sessions = sessions;
    }

    @Override
    public Optional<BadgeType> create(String username) {
        int studylogCount = badgeRepository.countStudylogByUsernameDuringSessions(username, sessions);

        if (studylogCount >= PASSION_KING_CRITERIA) {
            return Optional.of(BadgeType.PASSION_KING);
        }
        return Optional.empty();
    }
}
