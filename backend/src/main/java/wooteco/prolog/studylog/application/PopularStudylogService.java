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
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.Curriculum;
import wooteco.prolog.studylog.domain.PopularStudylog;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.PopularStudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PopularStudylogService {

    private static final int A_WEEK = 7;

    private final StudylogService studylogService;
    private final StudylogRepository studylogRepository;
    private final PopularStudylogRepository popularStudylogRepository;

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

    private void deleteAllLegacyPopularStudylogs() {
        List<PopularStudylog> popularStudylogs = popularStudylogRepository.findAllByDeletedFalse();

        for (PopularStudylog popularStudylog : popularStudylogs) {
            popularStudylog.delete();
        }
    }

    public PopularStudylogsResponse findPopularStudylogs(Pageable pageable, Long memberId,
                                                         boolean isAnonymousMember) {
        List<Studylog> allStudylogs = getSortedPopularStudyLogs();
        List<Studylog> frontStudylogs = getSortedCurriculumPopularStudylogs(allStudylogs,
            Curriculum.FRONTEND);
        List<Studylog> backStudylogs = getSortedCurriculumPopularStudylogs(allStudylogs,
            Curriculum.BACKEND);

        StudylogsResponse allResponse = getPageablePopularStudylogs(allStudylogs, pageable,
            memberId);
        StudylogsResponse frontResponse = getPageablePopularStudylogs(frontStudylogs, pageable,
            memberId);
        StudylogsResponse backResponse = getPageablePopularStudylogs(backStudylogs, pageable,
            memberId);

        if (isAnonymousMember) {
            return new PopularStudylogsResponse(allResponse, frontResponse, backResponse);
        }

        List<StudylogResponse> allData = allResponse.getData();
        updateScrapAndRead(allData, memberId);

        List<StudylogResponse> frontendData = frontResponse.getData();
        updateScrapAndRead(frontendData, memberId);

        List<StudylogResponse> backendData = backResponse.getData();
        updateScrapAndRead(backendData, memberId);

        return new PopularStudylogsResponse(allResponse, frontResponse, backResponse);
    }

    private List<Studylog> getSortedCurriculumPopularStudylogs(List<Studylog> allStudylogs,
                                                               Curriculum curriculum) {
        return allStudylogs.stream()
            .filter(
                studylog -> studylog.getSession().getName().contains(curriculum.getRegexPattern()))
            .collect(toList());
    }

    private StudylogsResponse getPageablePopularStudylogs(List<Studylog> allStudylogs,
                                                          Pageable pageable, Long memberId) {
        return StudylogsResponse.of(
            new PageImpl(allStudylogs, pageable, allStudylogs.size()),
            memberId
        );
    }

    private void updateScrapAndRead(List<StudylogResponse> studylogResponses, Long memberId) {
        studylogService.updateScrap(studylogResponses, studylogService.findScrapIds(memberId));
        studylogService.updateRead(studylogResponses, studylogService.findReadIds(memberId));
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
                return studylogs.stream()
                    .filter(it -> it.getSession().isSameAs(curriculum))
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList())
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
