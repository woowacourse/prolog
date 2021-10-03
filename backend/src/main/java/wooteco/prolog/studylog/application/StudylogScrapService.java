package wooteco.prolog.studylog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.studylog.exception.StudylogScrapNotExistException;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.exception.StudylogScrapAlreadyRegisteredException;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StudylogScrapService {

    private final StudylogScrapRepository studylogScrapRepository;
    private final MemberRepository memberRepository;
    private final StudylogRepository studylogRepository;

    @Transactional
    public MemberScrapResponse registerScrap(Long memberId, Long studyLogId) {
        if (studylogScrapRepository
            .countByMemberIdAndScrapStudylogId(memberId, studyLogId) > 0) {
            throw new StudylogScrapAlreadyRegisteredException();
        }

        Studylog studylog = studylogRepository.findById(studyLogId)
            .orElseThrow(StudylogNotFoundException::new);

        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        StudylogScrap studylogScrap = new StudylogScrap(member, studylog);
        studylogScrapRepository.save(studylogScrap);

        return MemberScrapResponse.of(studylogScrap);
    }

    @Transactional
    public void unregisterScrap(Long memberId, Long studyLogId) {
        StudylogScrap scrap = studylogScrapRepository
            .findByMemberIdAndStudylogId(memberId, studyLogId).orElseThrow(
                StudylogScrapNotExistException::new);

        studylogScrapRepository.delete(scrap);
    }

    public StudylogsResponse showScrap(Long memberId, Pageable pageable) {
        Page<StudylogScrap> membersScrap = studylogScrapRepository
            .findByMemberId(memberId, pageable);
        return StudylogsResponse.of(membersScrap.map(StudylogScrap::getStudylog));
    }

}
