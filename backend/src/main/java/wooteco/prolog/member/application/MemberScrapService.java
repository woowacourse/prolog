package wooteco.prolog.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberScrap;
import wooteco.prolog.member.domain.repository.MemberScrapRepository;
import wooteco.prolog.member.exception.MemberScrapAlreadyRegisteredException;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberScrapService {

    private final MemberScrapRepository memberScrapRepository;
    private final StudylogRepository studylogRepository;

    @Transactional
    public MemberScrapResponse insertScrap(Member member, Long studyLogId) {
        if (memberScrapRepository.countByMemberIdAndScrapStudylogId(member.getId(), studyLogId) > 0) {
            throw new MemberScrapAlreadyRegisteredException();
        }

        Studylog studylog = studylogRepository.findById(studyLogId)
            .orElseThrow(StudylogNotFoundException::new);

        MemberScrap memberScrap = new MemberScrap(member, studylog);
        memberScrapRepository.save(memberScrap);
        return MemberScrapResponse.of(memberScrap);
    }
}
