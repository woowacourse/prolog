package wooteco.prolog.admin.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Curriculum;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CurriculumResponse {

    private Long id;
    private String name;

    public CurriculumResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CurriculumResponse of(Curriculum curriculum) {
        if (curriculum == null) {
            return null;
        }
        return new CurriculumResponse(curriculum.getId(), curriculum.getName());
    }
}
