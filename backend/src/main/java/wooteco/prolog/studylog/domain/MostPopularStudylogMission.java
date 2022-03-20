package wooteco.prolog.studylog.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * wooteco.prolog.session.application.dto.MissionResponse 클래스에 기반을 둔 도메인
 * @author Hyeon9mak
 */
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MostPopularStudylogMission {

    private Long id;
    private String name;

    // TODO: 2022/03/20 현구 - Embeddable 클래스 내부에 Embedded는 어떻게 적용되나 확인
    @Embedded
    private MostPopularStudylogMissionLevel level;
}
