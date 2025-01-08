package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Answer;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogsResponse {

    private static final int ONE_INDEXED_PARAMETER = 1;

    private List<StudylogResponse> data;
    private Long totalSize;
    private int totalPage;
    private int currPage;

    public static StudylogsResponse of(Page<Studylog> page) {
        return of(page, null);
    }

    public static StudylogsResponse of(Page<Studylog> page, Long memberId, Map<Long, Long> commentCounts) {
        Page<StudylogResponse> responsePage = new PageImpl<>(
            toResponses(page.getContent(), memberId, commentCounts),
            page.getPageable(),
            page.getTotalElements()
        );

        return new StudylogsResponse(responsePage.getContent(),
            responsePage.getTotalElements(),
            responsePage.getTotalPages(),
            responsePage.getNumber() + ONE_INDEXED_PARAMETER);
    }

    public static StudylogsResponse of(Page<Studylog> page, Map<Long, List<Answer>> answers, Long memberId,
                                       Map<Long, Long> commentCounts) {
        Page<StudylogResponse> responsePage = new PageImpl<>(
            toResponses(page.getContent(), answers, memberId, commentCounts),
            page.getPageable(),
            page.getTotalElements()
        );

        return new StudylogsResponse(responsePage.getContent(),
            responsePage.getTotalElements(),
            responsePage.getTotalPages(),
            responsePage.getNumber() + ONE_INDEXED_PARAMETER);
    }

    public static StudylogsResponse of(Page<Studylog> page, Long memberId) {
        Page<StudylogResponse> responsePage = new PageImpl<>(
            toResponses(page.getContent(), memberId),
            page.getPageable(),
            page.getTotalElements()
        );

        return new StudylogsResponse(responsePage.getContent(),
            responsePage.getTotalElements(),
            responsePage.getTotalPages(),
            responsePage.getNumber() + ONE_INDEXED_PARAMETER);
    }

    public static StudylogsResponse of(
        List<Studylog> studylogs,
        long totalSize,
        int totalPage,
        int currPage,
        Long memberId,
        Map<Long, Long> commentCounts) {

        List<StudylogResponse> studylogResponses = convertToStudylogResponse(studylogs, memberId, commentCounts);
        return new StudylogsResponse(studylogResponses,
            totalSize,
            totalPage,
            currPage + ONE_INDEXED_PARAMETER);
    }

    private static List<StudylogResponse> convertToStudylogResponse(List<Studylog> studylogs,
                                                                    Long memberId,
                                                                    Map<Long, Long> commentCounts) {
        return studylogs.stream()
            .map(studylog -> StudylogResponse.of(studylog, memberId,
                commentCounts.get(studylog.getId())))
            .collect(toList());
    }

    private static List<StudylogResponse> toResponses(List<Studylog> studylogs, Long memberId,
                                                      Map<Long, Long> commentCounts) {
        return studylogs.stream()
            .map(studylog -> toResponse(studylog, memberId, commentCounts.get(studylog.getId())))
            .collect(toList());
    }

    private static List<StudylogResponse> toResponses(List<Studylog> studylogs, Map<Long, List<Answer>> answers,
                                                      Long memberId,
                                                      Map<Long, Long> commentCounts) {
        return studylogs.stream()
            .map(studylog -> toResponse(studylog, answers.get(studylog.getId()), memberId,
                commentCounts.get(studylog.getId())))
            .collect(toList());
    }

    private static List<StudylogResponse> toResponses(List<Studylog> studylogs, Long memberId) {
        return studylogs.stream().map(studylog -> toResponse(studylog, memberId)).collect(toList());
    }

    private static StudylogResponse toResponse(Studylog studylog, Long memberId, long commentCount) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        final List<Tag> tags = studylogTags.stream()
            .map(StudylogTag::getTag)
            .collect(toList());

        return new StudylogResponse(
            studylog,
            SessionResponse.of(studylog.getSession()),
            MissionResponse.of(studylog.getMission()),
            toResponse(tags),
            studylog.likedByMember(memberId), commentCount
        );
    }

    private static StudylogResponse toResponse(Studylog studylog, List<Answer> answers, Long memberId,
                                               long commentCount) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        final List<Tag> tags = studylogTags.stream()
            .map(StudylogTag::getTag)
            .collect(toList());

        return new StudylogResponse(
            studylog,
            SessionResponse.of(studylog.getSession()),
            MissionResponse.of(studylog.getMission()),
            AnswerResponse.listOf(answers),
            toResponse(tags),
            studylog.likedByMember(memberId), commentCount
        );
    }

    private static StudylogResponse toResponse(Studylog studylog, Long memberId) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        final List<Tag> tags = studylogTags.stream()
            .map(StudylogTag::getTag)
            .collect(toList());

        return new StudylogResponse(
            studylog,
            SessionResponse.of(studylog.getSession()),
            MissionResponse.of(studylog.getMission()),
            toResponse(tags),
            studylog.likedByMember(memberId), 0
        );
    }

    private static List<TagResponse> toResponse(List<Tag> tags) {
        return tags.stream()
            .map(TagResponse::of)
            .collect(Collectors.toList());
    }
}
