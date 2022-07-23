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
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.repository.GroupMemberRepository;
import wooteco.prolog.member.domain.repository.MemberGroupRepository;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
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
    private final MemberGroupRepository memberGroupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void updatePopularStudylogs(Pageable pageable) {
        deleteAllLegacyPopularStudylogs();

        List<MemberGroup> memberGroups = memberGroupRepository.findAll();
        List<Studylog> studylogs = new ArrayList<>();

        for (MemberGroup memberGroup : memberGroups) {
            List<Studylog> groupStudylogs = findStudylogsByDays(pageable, LocalDateTime.now(), memberGroup);
            studylogs.addAll(groupStudylogs);
        }
        List<PopularStudylog> popularStudylogs = studylogs.stream()
                .map(it -> new PopularStudylog(it.getId()))
                .collect(toList());

        popularStudylogRepository.saveAll(popularStudylogs);
    }

//    @Transactional
//    public void updatePopularStudylogs(Pageable pageable) {
//        deleteAllLegacyPopularStudylogs();
//
//        List<Studylog> studylogs = findStudylogsByDays(pageable, LocalDateTime.now());
//        List<PopularStudylog> popularStudylogs = studylogs.stream()
//            .map(it -> new PopularStudylog(it.getId()))
//            .collect(toList());
//
//        popularStudylogRepository.saveAll(popularStudylogs);
//    }

    public StudylogsResponse findPopularStudylogs(Pageable pageable, Long memberId, boolean isAnonymousMember) {

        List<MemberGroup> memberGroups = memberGroupRepository.findAll();

        List<Studylog> all = getSortedPopularStudyLogs();
        List<Studylog> backend = getSortedPopularStudyLogs(memberGroups.get(1));
        List<Studylog> frontend = getSortedPopularStudyLogs(memberGroups.get(0));
        PopularStudylogsResponse response = PopularStudylogsResponse.of(all, backend, frontend);
        System.out.println("response = " + response.getAllResponse().getData().size());
        System.out.println("response = " + response.getBackResponse().getData().size());
        System.out.println("response = " + response.getFrontResponse().getData().size());

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

    private List<Studylog> getSortedPopularStudyLogs(MemberGroup memberGroup) {
        return studylogRepository.findAllById(getPopularStudylogIds())
                .stream()
                .filter(it -> groupMemberRepository.existsGroupMemberByMemberAndGroup(it.getMember(),
                        memberGroup))
                .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                .collect(toList());
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

    private List<Studylog> findStudylogsByDays(Pageable pageable, LocalDateTime dateTime, MemberGroup memberGroup) {
        int decreaseDays = 0;
        int searchFailedCount = 0;

        // 스터디로그 작성한 사람이 FrontEnd인지, BackEnd인지만 판단해서 studylog를 popularStudylog로 넣어주기

        while (true) {
            decreaseDays += A_WEEK;
            List<Studylog> studylogs = studylogRepository.findByPastDays(dateTime.minusDays(decreaseDays));

            if (studylogs.size() >= pageable.getPageSize()) {
                List<Studylog> filteringStudylogs = studylogs.stream()
                        .filter(it -> groupMemberRepository.existsGroupMemberByMemberAndGroup(it.getMember(),
                                memberGroup))
                        .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                        .collect(toList());

                if (filteringStudylogs.size() < pageable.getPageSize()) {
                    return filteringStudylogs;
                }
                return filteringStudylogs
                        .subList(0, pageable.getPageSize());
            }

            if (searchFailedCount >= 2) {
                return studylogs.stream()
                        .filter(it -> groupMemberRepository.existsGroupMemberByMemberAndGroup(it.getMember(),
                                memberGroup))
                        .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                        .collect(toList());
            }

            searchFailedCount += 1;
        }
    }

    /**
     * 전체는 : 그냥 studylog 모든 것들 or 백엔드, 프론트엔드가 아닌 것
     */
}
