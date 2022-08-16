package wooteco.prolog.badge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wooteco.prolog.badge.application.BadgeCreator;
import wooteco.prolog.badge.application.ComplimentKingBadgeCreator;
import wooteco.prolog.badge.application.PassionKingBadgeCreator;
import wooteco.prolog.badge.domain.FourthCrewSessions;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@Configuration
public class BadgeCreatorConfig {

    @Bean
    public BadgeCreator levelTwoPassionKingBadgeCreator(BadgeRepository badgeRepository) {
        return new PassionKingBadgeCreator(badgeRepository, FourthCrewSessions.LEVEL_TWO.getSessionIds());
    }

    @Bean
    public BadgeCreator levelTwoComplimentKingBadgeCreator(BadgeRepository badgeRepository) {
        return new ComplimentKingBadgeCreator(badgeRepository, FourthCrewSessions.LEVEL_TWO.getSessionIds());
    }

    @Bean
    public BadgeCreator levelThreePassionKingBadgeCreator(BadgeRepository badgeRepository) {
        return new PassionKingBadgeCreator(badgeRepository, FourthCrewSessions.LEVEL_THREE.getSessionIds());
    }

    @Bean
    public BadgeCreator levelThreeComplimentKingBadgeCreator(BadgeRepository badgeRepository) {
        return new ComplimentKingBadgeCreator(badgeRepository, FourthCrewSessions.LEVEL_THREE.getSessionIds());
    }
}
