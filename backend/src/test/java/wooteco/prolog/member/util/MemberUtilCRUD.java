package wooteco.prolog.member.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;

@Component
public class MemberUtilCRUD {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Member 등록(MemberFixture memberFixture) {
        return memberRepository.findByUsername(memberFixture.getMemberName())
            .orElseGet(() -> memberRepository.save(memberFixture.asDomain()));
    }
}
