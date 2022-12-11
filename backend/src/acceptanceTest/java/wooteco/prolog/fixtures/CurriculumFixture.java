package wooteco.prolog.fixtures;

import wooteco.prolog.roadmap.application.dto.CurriculumRequest;

public class CurriculumFixture {

    public static CurriculumRequest 커리큘럼1_생성_요청_DTO() {
        return new CurriculumRequest("커리큘럼1");
    }

    public static CurriculumRequest 커리큘럼_수정_요청_DTO() {
        return new CurriculumRequest("수정된 커리큘럼");
    }

}
