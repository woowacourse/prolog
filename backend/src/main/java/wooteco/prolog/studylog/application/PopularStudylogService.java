package wooteco.prolog.studylog.application;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PopularStudylogService {

    private static final int A_WEEK = 7;
    private static final int THREE_WEEK = 21;
    private static final String BACKEND = "백엔드";
    private static final String FRONTEND = "프론트엔드";
    private static final String ANDROID = "안드로이드";

    private final StudylogService studylogService;
    private final StudylogRepository studylogRepository;
    private final PopularStudylogRepository popularStudylogRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void updatePopularStudylogs(Pageable pageable) {
        deleteAllLegacyPopularStudylogs();

        List<GroupMember> groupMembers = groupMemberRepository.findAll();
        Map<String, List<MemberGroup>> memberGroupsByPart = memberGroupRepository.findAll().stream()
            .collect(Collectors.groupingBy(MemberGroup::part));

        final List<Studylog> recentStudylogs = findRecentStudylogs(LocalDateTime.now(), pageable.getPageSize());

        List<Studylog> popularStudylogs = new ArrayList<>();

        for (String part : MemberGroup.parts()) {
            popularStudylogs.addAll(filterStudylogsByMemberGroups(recentStudylogs, new MemberGroups(memberGroupsByPart.get(part)), groupMembers)
                .stream()
                .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                .limit(pageable.getPageSize())
                .collect(toList()));
        }

        popularStudylogRepository.saveAll(popularStudylogs.stream()
            .map(it -> new PopularStudylog(it.getId()))
            .collect(toList()));
    }

    private void deleteAllLegacyPopularStudylogs() {
        List<PopularStudylog> popularStudylogs = popularStudylogRepository.findAllByDeletedFalse();

        for (PopularStudylog popularStudylog : popularStudylogs) {
            popularStudylog.delete();
        }
    }

    private List<Studylog> findRecentStudylogs(final LocalDateTime dateTime, final int minStudylogsSize) {
        int decreaseDays = A_WEEK;
        List<Studylog> recentStudylogs = new ArrayList<>();
        while (decreaseDays <= THREE_WEEK) {
            recentStudylogs = studylogRepository.findByPastDays(dateTime.minusDays(decreaseDays));
            if (recentStudylogs.size() >= minStudylogsSize) {
                return recentStudylogs;
            }
            decreaseDays += A_WEEK;
        }
        return recentStudylogs;
    }

    private List<Studylog> filterStudylogsByMemberGroups(final List<Studylog> studylogs, final MemberGroups memberGroups, final List<GroupMember> groupMembers) {
        return studylogs.stream()
            .filter(studylog -> checkMemberAssignedInMemberGroups(memberGroups, studylog.getMember(),
                groupMembers))
            .collect(toList());
    }

    private boolean checkMemberAssignedInMemberGroups(MemberGroups memberGroups,
                                                      Member member,
                                                      List<GroupMember> groupMembers) {
        if (canJudgeMemberGroup(groupMembers, member)) {
            return groupMembers.stream()
                .anyMatch(memberGroups::isContainsMemberGroups);
        }

        return false;
    }

    private boolean canJudgeMemberGroup(final List<GroupMember> groupMembers, final Member member) {
        return groupMembers.stream()
            .anyMatch(it -> it.getMember().equals(member));
    }

    public PopularStudylogsResponse findPopularStudylogs(Pageable pageable,
                                                         Long memberId,
                                                         boolean isAnonymousMember) {

        List<Studylog> allPopularStudylogs = getSortedPopularStudyLogs(pageable);
        List<GroupMember> groupedMembers = groupMemberRepository.findAll();
        Map<String, List<MemberGroup>> memberGroupsByPart = memberGroupRepository.findAll().stream()
            .collect(Collectors.groupingBy(MemberGroup::part));

        return PopularStudylogsResponse.of(
            studylogsResponse(allPopularStudylogs, pageable, memberId),
            studylogsResponse(filterStudylogsByMemberGroups(allPopularStudylogs, new MemberGroups(memberGroupsByPart.get(FRONTEND)), groupedMembers), pageable, memberId),
            studylogsResponse(filterStudylogsByMemberGroups(allPopularStudylogs, new MemberGroups(memberGroupsByPart.get(BACKEND)), groupedMembers), pageable, memberId),
            studylogsResponse(filterStudylogsByMemberGroups(allPopularStudylogs, new MemberGroups(memberGroupsByPart.get(ANDROID)), groupedMembers), pageable, memberId)
        );
    }

    private List<Studylog> getSortedPopularStudyLogs(Pageable pageable) {
        return studylogRepository.findAllByIdIn(getPopularStudylogIds(), pageable)
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

    private StudylogsResponse studylogsResponse(final List<Studylog> studylogs, final Pageable pageable, final Long memberId) {
        final PageImpl<Studylog> page = new PageImpl<>(studylogs, pageable, studylogs.size());
        if (memberId == null) {
            return StudylogsResponse.of(page);
        }
        final StudylogsResponse studylogsResponse = StudylogsResponse.of(page, memberId);
        checkStudylogScrapAndRead(studylogsResponse.getData(), memberId);
        return studylogsResponse;
    }

    private void checkStudylogScrapAndRead(List<StudylogResponse> data, Long memberId) {
        studylogService.updateScrap(data, studylogService.findScrapIds(memberId));
        studylogService.updateRead(data, studylogService.findReadIds(memberId));
    }
}
