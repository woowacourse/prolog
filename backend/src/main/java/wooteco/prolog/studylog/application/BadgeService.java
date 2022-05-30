package wooteco.prolog.studylog.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Badge;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@Service
public class BadgeService {

  private final MemberService memberService;
  private final BadgeRepository badgeRepository;

  public BadgeService(MemberService memberService,
                      BadgeRepository badgeRepository) {
    this.memberService = memberService;
    this.badgeRepository = badgeRepository;
  }


  public List<Badge> findBadges(String username, List<Long> sessions) {
    Member member = memberService.findByUsername(username);
    int count = badgeRepository.countStudylogByUsernameDuringSessions(username, sessions);

    if (count >= 7) {
      return Collections.singletonList(new Badge("url", "열정왕"));
    }
    return new ArrayList<>();
  }
}
