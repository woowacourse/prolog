package wooteco.prolog.studylog.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Badge;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final MemberService memberService;
    private final BadgeRepository badgeRepository;

    public List<Badge> findBadges(String username, List<Long> sessions) {
        Member member = memberService.findByUsername(username);

        int studylogCount = badgeRepository.countStudylogByUsernameDuringSessions(
            member.getUsername(),
            sessions);

        int likeCount = badgeRepository.countLikesByUsernameDuringSessions(
            member.getUsername(), sessions);

        return createBadges(studylogCount, likeCount);
    }

    private List<Badge> createBadges(int studylogCount, int likeCount) {
        List<Badge> badges = new ArrayList<>();

        if (isPassionKing(studylogCount)) {
            badges.add(new Badge("열정왕"));
        }

        if (isComplimentKing(likeCount)) {
            badges.add(new Badge("칭찬왕"));
        }

        return badges;
    }

    private boolean isPassionKing(int studylogCount) {
        return studylogCount >= 7;
    }

    private boolean isComplimentKing(int likeCount) {
        return likeCount >= 15;
    }
}
