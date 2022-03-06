package wooteco.prolog.studylog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudylogMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studylogId;

    private Long missionId;

    public StudylogMission(Long studylogId, Long missionId) {
        this.studylogId = studylogId;
        this.missionId = missionId;
    }

    public void updateMissionId(Long missionId) {
        this.missionId = missionId;
    }
}
