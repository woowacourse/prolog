package wooteco.prolog.studylog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.StudylogLikeResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.InvalidLikeRequestException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogLikeService {

    private final StudylogRepository studylogRepository;
    private final MemberService memberService;

    @Transactional
    public StudylogLikeResponse likeStudylog(Long memberId, Long studylogId, boolean isMember) {
        validIfMember(isMember);

        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        Member member = memberService.findById(memberId);

        studylog.like(member.getId());

        return new StudylogLikeResponse(true, studylog.getLikeCount());
    }

    @Transactional
    public StudylogLikeResponse unlikeStudylog(Long memberId, Long studylogId, boolean isMember) {
        validIfMember(isMember);

        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        Member member = memberService.findById(memberId);

        studylog.unlike(member.getId());

        return new StudylogLikeResponse(false, studylog.getLikeCount());
    }

    private void validIfMember(boolean isMember) {
        if (!isMember) {
            throw new InvalidLikeRequestException();
        }
    }
}
