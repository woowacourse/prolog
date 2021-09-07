package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
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
        Page<StudylogResponse> responsePage = page.map(StudylogsResponse::toResponse);
        return new StudylogsResponse(responsePage.getContent(),
            responsePage.getTotalElements(),
            responsePage.getTotalPages(),
            responsePage.getNumber() + ONE_INDEXED_PARAMETER);
    }

    private static StudylogResponse toResponse(Studylog studylog) {
        List<StudylogTag> studylogTags = studylog.getStudylogTags();
        final List<Tag> tags = studylogTags.stream()
            .map(StudylogTag::getTag)
            .collect(toList());

        return new StudylogResponse(studylog, MissionResponse.of(studylog.getMission()),
            toResponse(tags));
    }

    private static List<TagResponse> toResponse(List<Tag> tags) {
        return tags.stream()
            .map(TagResponse::of)
            .collect(Collectors.toList());
    }

}
