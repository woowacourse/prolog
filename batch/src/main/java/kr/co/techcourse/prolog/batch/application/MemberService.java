package kr.co.techcourse.prolog.batch.application;

import kr.co.techcourse.prolog.batch.domain.Member;
import kr.co.techcourse.prolog.batch.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(String name) {
        memberRepository.save(new Member(null, name));
    }

    public String getMemberName(String name) {
        return memberRepository.findByName(name).orElseThrow().getName();
    }
}
