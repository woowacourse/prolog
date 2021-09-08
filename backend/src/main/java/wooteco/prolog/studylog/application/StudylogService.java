package wooteco.prolog.studylog.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.exception.StudylogArgumentException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogService {

    private final StudylogRepository studylogRepository;
    private final StudylogDocumentService studylogDocumentService;
    private final MissionService missionService;
    private final MemberService memberService;
    private final TagService tagService;

    public StudylogsResponse findStudylogsWithFilter(
        StudylogsSearchRequest studylogsSearchRequest) {

        final String keyword = studylogsSearchRequest.getKeyword();
        final Pageable pageable = studylogsSearchRequest.getPageable();

        List<Long> studylogIds = Collections.emptyList();
        if (isSearch(keyword)) {
            studylogIds = studylogDocumentService.findBySearchKeyword(keyword, pageable);
        }

        if (studylogsSearchRequest.hasOnlySearch()) {
            return StudylogsResponse.of(studylogRepository.findByIdIn(studylogIds, pageable));
        }

        Page<Studylog> studylogs = studylogRepository
            .findAll(makeSpecifications(studylogsSearchRequest, studylogIds), pageable);

        return StudylogsResponse.of(studylogs);
    }

    private Specification<Studylog> makeSpecifications(
        StudylogsSearchRequest studylogsSearchRequest, List<Long> studylogIds
    ) {
        return StudylogSpecification.findByLevelIn(studylogsSearchRequest.getLevels())
            .and(StudylogSpecification.equalIn("id", studylogIds,
                                               isSearch(studylogsSearchRequest.getKeyword())))
            .and(StudylogSpecification.equalIn("mission", studylogsSearchRequest.getMissions()))
            .and(StudylogSpecification.findByTagIn(studylogsSearchRequest.getTags()))
            .and(StudylogSpecification.findByUsernameIn(studylogsSearchRequest.getUsernames()))
            .and(StudylogSpecification.distinct(true));
    }

    private boolean isSearch(String searchKeyword) {
        return Objects.nonNull(searchKeyword) && !searchKeyword.isEmpty();
    }

    public StudylogsResponse findStudylogsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return StudylogsResponse.of(studylogRepository.findByMember(member, pageable));
    }

    @Transactional
    public List<StudylogResponse> insertStudylogs(Member member,
                                                  List<StudylogRequest> studylogRequests) {
        if (studylogRequests.size() == 0) {
            throw new StudylogArgumentException();
        }

        return studylogRequests.stream()
            .map(studylogRequest -> insertStudylog(member, studylogRequest))
            .collect(Collectors.toList());
    }

    private StudylogResponse insertStudylog(Member member, StudylogRequest studylogRequest) {
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        Mission mission = missionService.findById(studylogRequest.getMissionId());

        Studylog requestedStudylog = new Studylog(member,
                                                  studylogRequest.getTitle(),
                                                  studylogRequest.getContent(),
                                                  mission,
                                                  tags.getList());

        Studylog createdStudylog = studylogRepository.save(requestedStudylog);

        studylogDocumentService.save(createdStudylog.toStudylogDocument());
        return StudylogResponse.of(createdStudylog);
    }

    public StudylogResponse findById(Long id) {
        Studylog studylog = studylogRepository.findById(id)
            .orElseThrow(StudylogNotFoundException::new);

        return StudylogResponse.of(studylog);
    }

    @Transactional
    public void updateStudylog(Member member, Long studylogId, StudylogRequest studylogRequest) {
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(member);

        Mission mission = missionService.findById(studylogRequest.getMissionId());
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        studylog.update(studylogRequest.getTitle(), studylogRequest.getContent(), mission, tags);

        studylogDocumentService.update(studylog.toStudylogDocument());
    }

    @Transactional
    public void deleteStudylog(Member member, Long studylogId) {
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(member);

        studylogDocumentService.delete(studylog.toStudylogDocument());
        studylogRepository.delete(studylog);
    }
}
