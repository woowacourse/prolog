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
import wooteco.prolog.studyLogDocument.application.StudyLogDocumentService;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.StudyLogsSearchRequest;
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
    private final StudyLogDocumentService studyLogDocumentService;
    private final MissionService missionService;
    private final MemberService memberService;
    private final TagService tagService;

    public StudylogsResponse findStudylogsWithFilter(
        StudyLogsSearchRequest studyLogsSearchRequest) {

        final String keyword = studyLogsSearchRequest.getKeyword();
        final Pageable pageable = studyLogsSearchRequest.getPageable();

        List<Long> studyLogIds = Collections.emptyList();
        if (isSearch(keyword)) {
            studyLogIds = studyLogDocumentService.findBySearchKeyword(keyword, pageable);
        }

        if (studyLogsSearchRequest.hasOnlySearch()) {
            return StudylogsResponse.of(studylogRepository.findByIdIn(studyLogIds, pageable));
        }

        Page<Studylog> studylogs = studylogRepository
            .findAll(makeSpecifications(studyLogsSearchRequest, studyLogIds), pageable);

        return StudylogsResponse.of(studylogs);
    }

    private Specification<Studylog> makeSpecifications(
        StudyLogsSearchRequest studyLogsSearchRequest, List<Long> studyLogIds
    ) {
        return StudylogSpecification.findByLevelIn(studyLogsSearchRequest.getLevels())
            .and(StudylogSpecification.equalIn("id", studyLogIds,
                                               isSearch(studyLogsSearchRequest.getKeyword())))
            .and(StudylogSpecification.equalIn("mission", studyLogsSearchRequest.getMissions()))
            .and(StudylogSpecification.findByTagIn(studyLogsSearchRequest.getTags()))
            .and(StudylogSpecification.findByUsernameIn(studyLogsSearchRequest.getUsernames()))
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

        studyLogDocumentService.save(
            new StudyLogDocument(createdStudylog.getId(), createdStudylog.getTitle(),
                                 createdStudylog.getContent()));

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
    }

    @Transactional
    public void deleteStudylog(Member member, Long studylogId) {
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(member);

        studylogRepository.delete(studylog);
    }
}
