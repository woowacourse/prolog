package kr.co.techcourse.prolog.batch.job.sample.tasklet.east.service;

import kr.co.techcourse.prolog.batch.job.sample.tasklet.east.domain.Member;
import kr.co.techcourse.prolog.batch.job.sample.tasklet.east.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("east.MemberService")
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long create(String name) {
        return memberRepository.save(new Member(name)).getId();
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
