package wooteco.prolog.badge.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.badge.application.BadgeService;
import wooteco.prolog.badge.application.dto.BadgeResponse;
import wooteco.prolog.badge.application.dto.BadgesResponse;
import wooteco.prolog.badge.domain.BadgeType;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping(value = "/{username}/badges", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BadgesResponse> findMemberBadges(@PathVariable String username) {
        List<BadgeType> badges = badgeService.getBadges(username);
        List<BadgeResponse> badgeResponses = badges.stream()
            .map(BadgeType::toString)
            .map(BadgeResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(new BadgesResponse(badgeResponses));
    }
}
