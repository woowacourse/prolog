package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogWithScrapedCountResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogsWithScrapCountResponse;
import wooteco.prolog.studylog.domain.Curriculum;
import wooteco.prolog.studylog.domain.PopularStudylog;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.PopularStudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PopularStudylogService {

    private static final int ONE_INDEXED_PARAMETER = 1;
    private static final int A_WEEK = 7;

    private final StudylogService studylogService;
    private final StudylogRepository studylogRepository;
    private final PopularStudylogRepository popularStudylogRepository;
    private final StudylogScrapRepository studylogScrapRepository;

    @Transactional
    public void updatePopularStudylogs(Pageable pageable) {
        deleteAllLegacyPopularStudylogs();
        List<Studylog> studylogs = new ArrayList<>();
        for (Curriculum curriculum : Curriculum.values()) {
            List<Studylog> studylogsByDays = findStudylogsByDays(pageable, LocalDateTime.now(),
                curriculum);
            studylogs.addAll(studylogsByDays);
        }

        List<PopularStudylog> popularStudylogs = studylogs.stream()
            .map(it -> new PopularStudylog(it.getId()))
            .collect(toList());

        popularStudylogRepository.saveAll(popularStudylogs);
    }

    public PopularStudylogsResponse findPopularStudylogs(Pageable pageable, Long memberId,
        boolean isAnonymousMember) {
        List<Studylog> allStudylogs = getSortedPopularStudyLogs();
        List<Studylog> frontStudylogs = getSortedCurriculumPopularStudylogs(allStudylogs,
            Curriculum.FRONTEND);
        List<Studylog> backStudylogs = getSortedCurriculumPopularStudylogs(allStudylogs,
            Curriculum.BACKEND);

        StudylogsWithScrapCountResponse allResponse = getPageablePopularStudylogs(allStudylogs, pageable,
            memberId);
        StudylogsWithScrapCountResponse frontResponse = getPageablePopularStudylogs(frontStudylogs, pageable,
            memberId);
        StudylogsWithScrapCountResponse backResponse = getPageablePopularStudylogs(backStudylogs, pageable,
            memberId);

        if (isAnonymousMember) {
            return new PopularStudylogsResponse(allResponse, frontResponse, backResponse);
        }

        List<StudylogResponse> allData = allResponse.getStudylogResponses();
        updateScrapAndRead(allData, memberId);

        List<StudylogResponse> frontendData = frontResponse.getStudylogResponses();
        updateScrapAndRead(frontendData, memberId);

        List<StudylogResponse> backendData = backResponse.getStudylogResponses();
        updateScrapAndRead(backendData, memberId);

        return new PopularStudylogsResponse(allResponse, frontResponse, backResponse);
    }

    private List<Studylog> getSortedCurriculumPopularStudylogs(List<Studylog> allStudylogs,
        Curriculum curriculum) {

        return allStudylogs.stream()
            .filter(studylog -> studylog.isContainsCurriculum(curriculum))
            .collect(toList());
    }

    private StudylogsWithScrapCountResponse getPageablePopularStudylogs(List<Studylog> allStudylogs,
        Pageable pageable, Long memberId) {

        final List<StudylogWithScrapedCountResponse> studylogsResponse = allStudylogs.stream()
            .map(studylog -> new StudylogWithScrapedCountResponse(StudylogResponse.of(studylog),
                studylogScrapRepository.countByStudylogId(studylog.getId())))
            .collect(toList());

        return new StudylogsWithScrapCountResponse(studylogsResponse, 0L, 0, 0);
    }

    private void updateScrapAndRead(List<StudylogResponse> studylogResponses, Long memberId) {
        studylogService.updateScrap(studylogResponses, studylogService.findScrapIds(memberId));
        studylogService.updateRead(studylogResponses, studylogService.findReadIds(memberId));
    }

    private void deleteAllLegacyPopularStudylogs() {
        List<PopularStudylog> popularStudylogs = popularStudylogRepository.findAllByDeletedFalse();

        for (PopularStudylog popularStudylog : popularStudylogs) {
            popularStudylog.delete();
        }
    }

    private List<Studylog> getSortedPopularStudyLogs() {
        return studylogRepository.findAllById(getPopularStudylogIds())
            .stream()
            .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
            .collect(toList());
    }

    private List<Long> getPopularStudylogIds() {
        return popularStudylogRepository.findAllByDeletedFalse()
            .stream()
            .map(PopularStudylog::getStudylogId)
            .collect(toList());
    }

    private List<Studylog> findStudylogsByDays(Pageable pageable, LocalDateTime dateTime,
        Curriculum curriculum) {
        int decreaseDays = 0;
        int searchFailedCount = 0;

        while (true) {
            decreaseDays += A_WEEK;
            List<Studylog> studylogs = studylogRepository.findByPastDays(
                dateTime.minusDays(decreaseDays));

            if (studylogs.size() >= pageable.getPageSize()) {
                final List<Studylog> filteredStudylogs = studylogs.stream()
                    .filter(it -> it.getSession().isSameAs(curriculum))
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList());
                if (filteredStudylogs.size() < pageable.getPageSize()) {
                    return filteredStudylogs;
                }
                return filteredStudylogs
                    .subList(0, pageable.getPageSize());
            }

            if (searchFailedCount >= 2) {
                return studylogs.stream()
                    .filter(it -> it.getSession().isSameAs(curriculum))
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList());
            }

            searchFailedCount += 1;
        }
    }
}
