package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
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

        List<Studylog> studylogs = findStudylogsByDays(pageable, LocalDateTime.now());
        List<PopularStudylog> popularStudylogs = studylogs.stream()
            .map(it -> new PopularStudylog(it.getId()))
            .collect(toList());

        popularStudylogRepository.saveAll(popularStudylogs);
    }

    public StudylogsResponse findPopularStudylogs(Pageable pageable, Long memberId,
        boolean isAnonymousMember) {
        List<Studylog> studylogs = getSortedPopularStudyLogs();
        PageImpl<Studylog> page = new PageImpl<>(studylogs, pageable, studylogs.size());
        StudylogsResponse studylogsResponse = StudylogsResponse.of(page, memberId);

        if (isAnonymousMember) {
            return studylogsResponse;
        }
        List<StudylogResponse> data = studylogsResponse.getData();
        studylogService.updateScrap(data, studylogService.findScrapIds(memberId));
        studylogService.updateRead(data, studylogService.findReadIds(memberId));
        return studylogsResponse;
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

    private List<Studylog> findStudylogsByDays(Pageable pageable, LocalDateTime dateTime) {
        int decreaseDays = 0;
        int searchFailedCount = 0;

        while (true) {
            decreaseDays += A_WEEK;
            List<Studylog> studylogs = studylogRepository.findByPastDays(
                dateTime.minusDays(decreaseDays));

            if (studylogs.size() >= pageable.getPageSize()) {
                return studylogs.stream()
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList())
                    .subList(0, pageable.getPageSize());
            }

            if (searchFailedCount >= 2) {
                return studylogs.stream()
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList());
            }

            searchFailedCount += 1;
        }
    }
}
