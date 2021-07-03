package wooteco.prolog.member.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.dao.MemberDao;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.exception.MemberNotAllowedException;
import wooteco.prolog.member.exception.MemberNotFoundException;

@Service
@AllArgsConstructor
public class MemberService {
    private MemberDao memberDao;

    public Member findById(Long id) {
        return memberDao.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
    }

    public MemberResponse findMemberByUsername(String username) {
        Member member = findByUsername(username);
        return MemberResponse.of(member);
    }

    public void updateMember(Member member, String username, MemberUpdateRequest updateRequest) {
        Member persistMember = findByUsername(username);
        validateMember(member, persistMember);

        persistMember.update(updateRequest.getUsername(), updateRequest.getNickname(), updateRequest.getImageUrl());

        memberDao.updateMember(persistMember);
    }

    private void validateMember(Member member, Member persistMember) {
        if (!member.getId().equals(persistMember.getId())) {
            throw new MemberNotAllowedException();
        }
    }
}
