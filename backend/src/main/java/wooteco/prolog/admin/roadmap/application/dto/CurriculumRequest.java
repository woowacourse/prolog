package wooteco.prolog.admin.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Curriculum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurriculumRequest {

    private String name;

    public CurriculumRequest(String name) {
        this.name = name;
    }

    public Curriculum toEntity() {
        return new Curriculum(this.name);
    }
}
