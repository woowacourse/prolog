package wooteco.prolog.admin.roadmap.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.admin.roadmap.application.dto.CurriculumResponse;
import wooteco.prolog.roadmap.domain.Curriculum;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CurriculumResponses {

    private List<wooteco.prolog.admin.roadmap.application.dto.CurriculumResponse> data;

    public CurriculumResponses(
        List<wooteco.prolog.admin.roadmap.application.dto.CurriculumResponse> data) {
        this.data = data;
    }

    public static CurriculumResponses createResponse(final List<Curriculum> curriculums) {
        List<wooteco.prolog.admin.roadmap.application.dto.CurriculumResponse> curriculumsResponse = curriculums.stream()
            .map(CurriculumResponse::of)
            .collect(Collectors.toList());
        return new CurriculumResponses(curriculumsResponse);
    }

}
