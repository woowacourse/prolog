package wooteco.prolog.studylog.application;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.exception.PostArgumentException;
import wooteco.prolog.studylog.exception.PostNotFoundException;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tags;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogService {

    private final StudylogRepository studylogRepository;
    private final MissionService missionService;
    private final StudylogTagService studylogTagService;
    private final MemberService memberService;
    private final TagService tagService;

    public StudylogsResponse findPostsWithFilter(
        List<Long> levelIds,
        List<Long> missionIds,
        List<Long> tagIds,
        List<String> usernames,
        Pageable pageable) {
        return findPostsWithFilter(levelIds, missionIds, tagIds, usernames, null, null, pageable);
    }

    public StudylogsResponse findPostsWithFilter(
        List<Long> levelIds,
        List<Long> missionIds,
        List<Long> tagIds,
        List<String> usernames,
        LocalDate startDate,
        LocalDate endDate,
        Pageable pageable) {

        Specification<Studylog> specs =
            StudylogSpecification.findByLevelIn(levelIds)
                .and(StudylogSpecification.equalIn("mission", missionIds))
                .and(StudylogSpecification.findByTagIn(tagIds))
                .and(StudylogSpecification.findByUsernameIn(usernames))
                .and(StudylogSpecification.findBetweenDate(startDate, endDate))
                .and(StudylogSpecification.distinct(true));

        Page<Studylog> posts = studylogRepository.findAll(specs, pageable);

        return StudylogsResponse.of(posts);
    }

    public StudylogsResponse findPostsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return StudylogsResponse.of(studylogRepository.findByMember(member, pageable));
    }

    @Transactional
    public List<StudylogResponse> insertPosts(Member member, List<StudylogRequest> studylogRequests) {
        if (studylogRequests.size() == 0) {
            throw new PostArgumentException();
        }
        final Member byId = memberService.findById(member.getId());

        return studylogRequests.stream()
            .map(postRequest -> insertPost(byId, postRequest))
            .collect(toList());
    }

    private StudylogResponse insertPost(Member member, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(member.getId());
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        Mission mission = missionService.findById(studylogRequest.getMissionId());

        Studylog requestedStudylog = new Studylog(foundMember,
            studylogRequest.getTitle(),
            studylogRequest.getContent(),
            mission,
            tags.getList());

        Studylog createdStudylog = studylogRepository.save(requestedStudylog);
        foundMember.addTags(tags);

        return StudylogResponse.of(createdStudylog);
    }

    public StudylogResponse findById(Long id) {
        Studylog studylog = studylogRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        return StudylogResponse.of(studylog);
    }

    @Transactional
    public void updatePost(Member member, Long postId, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(member.getId());
        Studylog studylog = studylogRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);
        studylog.validateAuthor(foundMember);
        final Tags originalTags = tagService.findByPostAndMember(studylog, foundMember);

        Mission mission = missionService.findById(studylogRequest.getMissionId());
        Tags newTags = tagService.findOrCreate(studylogRequest.getTags());

        studylog.update(studylogRequest.getTitle(), studylogRequest.getContent(), mission, newTags);
        foundMember.updateTags(originalTags, newTags);
    }

    @Transactional
    public void deletePost(Member member, Long postId) {
        final Member foundMember = memberService.findById(member.getId());
        Studylog studylog = studylogRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);
        studylog.validateAuthor(foundMember);

        studylogRepository.delete(studylog);
        final Tags tags = tagService.findByPostAndMember(studylog, foundMember);
        foundMember.removeTag(tags);
    }

    public List<CalendarStudylogResponse> findCalendarPosts(String username, LocalDate localDate) {
        final Member member = memberService.findByUsername(username);
        final LocalDateTime start = localDate.with(firstDayOfMonth()).atStartOfDay();
        final LocalDateTime end = localDate.with(lastDayOfMonth()).atTime(LocalTime.MAX);

        return studylogRepository.findByMemberBetween(member, start, end)
            .stream()
            .map(CalendarStudylogResponse::of)
            .collect(toList());
    }

    public int countPostByMember(Member member) {
        return studylogRepository.countByMember(member);
    }
}
