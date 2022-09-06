package wooteco.prolog.badge.application;

import java.util.Optional;
import wooteco.prolog.badge.domain.BadgeType;

public interface BadgeCreator {

    Optional<BadgeType> create(String username);
}
