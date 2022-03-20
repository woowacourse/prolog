package wooteco.prolog.studylog.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * wooteco.prolog.session.application.dto.LevelResponse 클래스에 기반을 둔 도메인
 * @author Hyeon9mak
 */
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MostPopularStudylogMissionLevel {

    private Long id;
    private String name;
}
