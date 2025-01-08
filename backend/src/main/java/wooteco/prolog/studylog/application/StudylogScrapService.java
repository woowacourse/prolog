package wooteco.prolog.studylog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.application.dto.MemberScrapResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;

import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_SCRAP_ALREADY_REGISTERED_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_SCRAP_NOT_EXIST_EXCEPTION;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StudylogScrapService {

    private final StudylogScrapRepository studylogScrapRepository;
    private final MemberRepository memberRepository;
    private final StudylogRepository studylogRepository;

    @Transactional
    public MemberScrapResponse registerScrap(Long memberId, Long studylogId) {
        if (studylogScrapRepository
            .countByMemberIdAndScrapStudylogId(memberId, studylogId) > 0) {
            throw new BadRequestException(STUDYLOG_SCRAP_ALREADY_REGISTERED_EXCEPTION);
        }

        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(MEMBER_NOT_ALLOWED));

        StudylogScrap studylogScrap = new StudylogScrap(member, studylog);
        studylogScrapRepository.save(studylogScrap);

        return MemberScrapResponse.of(studylogScrap);
    }

    @Transactional
    public void unregisterScrap(Long memberId, Long studylogId) {
        StudylogScrap scrap = studylogScrapRepository
            .findByMemberIdAndStudylogId(memberId, studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_SCRAP_NOT_EXIST_EXCEPTION));

        studylogScrapRepository.delete(scrap);
    }

    public StudylogsResponse showScrap(Long memberId, Pageable pageable) {
        Page<StudylogScrap> membersScrap = studylogScrapRepository
            .findByMemberId(memberId, pageable);
        return StudylogsResponse.of(membersScrap.map(StudylogScrap::getStudylog));
    }

}
