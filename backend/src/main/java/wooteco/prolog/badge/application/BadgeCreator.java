package wooteco.prolog.badge.application;

import wooteco.prolog.badge.domain.BadgeType;

import java.util.Optional;

public interface BadgeCreator {

    Optional<BadgeType> create(String username);
}
