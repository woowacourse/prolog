package wooteco.prolog.studylog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.BaseEntity;


/**
 * 홈페이지를 클릭할 때마다 매번 조회되는 테이블(엔티티).
 * 조회 시마다 다른 테이블들과 계속해서 JOIN이 발생함.
 * 조회 속도가 느릴 시 temp/mainpage-popular-studylog-api 브랜치 작업 내용을 다시 활용할 것
 * (해당 브랜치에는 화면단에 뿌려줄 RAW한 데이터들만 가진 테이블로 설계되어 있음)
 * @author hyeon9mak
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MostPopularStudylog extends BaseEntity {

    @Column(nullable = false)
    private Long studylogId;

    @Column(nullable = false)
    private boolean deleted;

    public MostPopularStudylog(Long studylogId) {
        this.studylogId = studylogId;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }
}
