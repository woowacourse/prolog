package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.MemberGroups;
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
    private static final String FRONTEND = "프론트엔드";
    private static final String BACKEND = "백엔드";

    private final StudylogService studylogService;
    private final StudylogRepository studylogRepository;
    private final PopularStudylogRepository popularStudylogRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void updatePopularStudylogs(Pageable pageable) {
        deleteAllLegacyPopularStudylogs();

        List<GroupMember> groupMembers = groupMemberRepository.findAll();
        Map<String, List<MemberGroup>> memberGroups = memberGroupRepository.findAll().stream()
            .collect(Collectors.groupingBy(MemberGroup::getGroupName));

        List<Studylog> studylogs = new ArrayList<>();
        studylogs.addAll(findStudylogsByDays(pageable, LocalDateTime.now(),
            new MemberGroups(memberGroups.get(FRONTEND)), groupMembers));
        studylogs.addAll(findStudylogsByDays(pageable, LocalDateTime.now(),
            new MemberGroups(memberGroups.get(BACKEND)), groupMembers));

        List<PopularStudylog> popularStudylogs = studylogs.stream()
            .map(it -> new PopularStudylog(it.getId()))
            .collect(toList());

        popularStudylogRepository.saveAll(popularStudylogs);
    }

    public PopularStudylogsResponse findPopularStudylogs(Pageable pageable,
                                                         Long memberId,
                                                         boolean isAnonymousMember) {

        List<GroupMember> groupMembers = groupMemberRepository.findAll();
        Map<String, List<MemberGroup>> memberGroups = memberGroupRepository.findAll().stream()
            .collect(Collectors.groupingBy(MemberGroup::getGroupName));

        List<Studylog> all = getSortedPopularStudyLogs(pageable);
        List<Studylog> frontend = getSortedPopularStudyLogs(all,
            new MemberGroups(memberGroups.get(FRONTEND)), groupMembers);
        List<Studylog> backend = getSortedPopularStudyLogs(all,
            new MemberGroups(memberGroups.get(BACKEND)), groupMembers);

        PageImpl<Studylog> allPage = new PageImpl<>(all, pageable, all.size());
        PageImpl<Studylog> frontendPage = new PageImpl<>(frontend, pageable, frontend.size());
        PageImpl<Studylog> backendPage = new PageImpl<>(backend, pageable, backend.size());

        StudylogsResponse allStudylogsResponse = StudylogsResponse.of(allPage, memberId);
        StudylogsResponse frontendStudylogsResponse = StudylogsResponse.of(frontendPage, memberId);
        StudylogsResponse backendStudylogsResponse = StudylogsResponse.of(backendPage, memberId);

        if (isAnonymousMember) {
            return PopularStudylogsResponse.of(
                allStudylogsResponse, frontendStudylogsResponse, backendStudylogsResponse);
        }

        List<StudylogResponse> allData = allStudylogsResponse.getData();
        List<StudylogResponse> frontendData = frontendStudylogsResponse.getData();
        List<StudylogResponse> backendData = backendStudylogsResponse.getData();

        checkStudylogScrapAndRead(allData, memberId);
        checkStudylogScrapAndRead(frontendData, memberId);
        checkStudylogScrapAndRead(backendData, memberId);

        return PopularStudylogsResponse.of(
            allStudylogsResponse, frontendStudylogsResponse, backendStudylogsResponse);
    }

    private List<Studylog> getSortedPopularStudyLogs(Pageable pageable) {
        return studylogRepository.findAllByIdIn(getPopularStudylogIds(), pageable)
            .stream()
            .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
            .collect(toList());
    }

    private List<Studylog> getSortedPopularStudyLogs(List<Studylog> studylogs,
                                                     MemberGroups memberGroups,
                                                     List<GroupMember> groupMembers) {
        return studylogs.stream()
            .filter(it -> isContainsGroupMemberOfMemberGroups(memberGroups, it.getMember(),
                groupMembers))
            .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
            .collect(toList());
    }

    private void checkStudylogScrapAndRead(List<StudylogResponse> data, Long memberId) {
        studylogService.updateScrap(data, studylogService.findScrapIds(memberId));
        studylogService.updateRead(data, studylogService.findReadIds(memberId));
    }

    private void deleteAllLegacyPopularStudylogs() {
        List<PopularStudylog> popularStudylogs = popularStudylogRepository.findAllByDeletedFalse();

        for (PopularStudylog popularStudylog : popularStudylogs) {
            popularStudylog.delete();
        }
    }

    private List<Long> getPopularStudylogIds() {
        return popularStudylogRepository.findAllByDeletedFalse()
            .stream()
            .map(PopularStudylog::getStudylogId)
            .collect(toList());
    }

    private List<Studylog> findStudylogsByDays(Pageable pageable,
                                               LocalDateTime dateTime,
                                               MemberGroups memberGroups,
                                               List<GroupMember> groupMembers) {
        int decreaseDays = 0;
        int searchFailedCount = 0;

        while (true) {
            decreaseDays += A_WEEK;
            List<Studylog> studylogs = studylogRepository.findByPastDays(
                dateTime.minusDays(decreaseDays));

            if (studylogs.size() >= pageable.getPageSize()) {
                List<Studylog> filteringStudylogs = studylogs.stream()
                    .filter(it -> isContainsGroupMemberOfMemberGroups(
                        memberGroups, it.getMember(), groupMembers))
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
                    .filter(it -> isContainsGroupMemberOfMemberGroups(
                        memberGroups, it.getMember(), groupMembers))
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList());
            }

            searchFailedCount += 1;
        }
    }

    private boolean isContainsGroupMemberOfMemberGroups(MemberGroups memberGroups,
                                                        Member member,
                                                        List<GroupMember> groupMembers) {
        return groupMembers.stream()
            .anyMatch(
                it -> it.getMember().equals(member) && memberGroups.isContainsMemberGroups(it));
    }
}
