package wooteco.prolog.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberScrapStudylog;
import wooteco.prolog.member.domain.repository.MemberScrapStudylogRepository;
import wooteco.prolog.member.exception.MemberScrapAlreadyRegisteredException;
import wooteco.prolog.member.exception.MemberScrapNotExistException;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberScrapService {

    private final MemberScrapStudylogRepository memberScrapStudylogRepository;
    private final StudylogRepository studylogRepository;

    @Transactional
    public MemberScrapResponse registerScrap(Member member, Long studyLogId) {
        if (memberScrapStudylogRepository
            .countByMemberIdAndScrapStudylogId(member.getId(), studyLogId) > 0) {
            throw new MemberScrapAlreadyRegisteredException();
        }

        Studylog studylog = studylogRepository.findById(studyLogId)
            .orElseThrow(StudylogNotFoundException::new);

        MemberScrapStudylog memberScrapStudylog = new MemberScrapStudylog(member, studylog);
        memberScrapStudylogRepository.save(memberScrapStudylog);
        return MemberScrapResponse.of(memberScrapStudylog);
    }

    @Transactional
    public void unregisterScrap(Member member, Long studyLogId) {
        MemberScrapStudylog memberScrapStudylog = memberScrapStudylogRepository
            .findByMemberIdAndScrapStudylogId(member.getId(), studyLogId)
            .orElseThrow(MemberScrapNotExistException::new);

        memberScrapStudylogRepository.delete(memberScrapStudylog);
    }
}
