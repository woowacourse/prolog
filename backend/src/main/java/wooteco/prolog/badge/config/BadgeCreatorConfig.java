package wooteco.prolog.badge.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wooteco.prolog.badge.application.BadgeCreator;
import wooteco.prolog.badge.application.ComplimentKingBadgeCreator;
import wooteco.prolog.badge.application.PassionKingBadgeCreator;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@Configuration
public class BadgeCreatorConfig {

    private static final List<Long> LEVEL_TWO_SESSION_IDS = Arrays.asList(10L, 11L);

    @Bean
    public BadgeCreator levelTwoPassionKingBadgeCreator(BadgeRepository badgeRepository) {
        return new PassionKingBadgeCreator(badgeRepository, LEVEL_TWO_SESSION_IDS);
    }

    @Bean
    public BadgeCreator levelTwoComplimentKingBadgeCreator(BadgeRepository badgeRepository) {
        return new ComplimentKingBadgeCreator(badgeRepository, LEVEL_TWO_SESSION_IDS);
    }
}
