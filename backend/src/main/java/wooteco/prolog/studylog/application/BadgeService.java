package wooteco.prolog.studylog.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.studylog.domain.Badge;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final MemberService memberService;
    private final BadgeRepository badgeRepository;

    public List<Badge> findBadges(String username, List<Long> sessions) {
        memberService.findByUsername(username);

        List<Badge> badges = new ArrayList<>();
        int count = badgeRepository.countStudylogByUsernameDuringSessions(username, sessions);

        if (count >= 7) {
            badges.add(new Badge("열정왕"));
        }

        count = badgeRepository.countLikesByUsernameDuringSessions(username, sessions);

        if (count >= 15) {
            badges.add(new Badge("칭찬왕"));
        }

        return badges;
    }
}
