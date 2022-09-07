package wooteco.prolog.badge.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;

@Service
public class BadgeService {

    private final MemberService memberService;
    private final List<BadgeCreator> badgeCreators;

    public BadgeService(MemberService memberService, List<BadgeCreator> badgeCreators) {
        this.memberService = memberService;
        this.badgeCreators = badgeCreators;
    }

    public List<BadgeType> getBadges(String username) {
        Member member = memberService.findByUsername(username);

        return badgeCreators.stream()
                .map(creator -> creator.create(member.getUsername()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
