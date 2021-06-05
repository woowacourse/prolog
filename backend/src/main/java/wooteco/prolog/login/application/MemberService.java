package wooteco.prolog.login.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.login.dao.MemberDao;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.excetpion.MemberNotFoundException;

@Service
@AllArgsConstructor
public class MemberService {
    private MemberDao memberDao;

    public Member findById(Long id){
        return memberDao.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다."));
    }
}
