package wooteco.prolog.studylog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.BaseEntity;

/**
 * wooteco.prolog.studylog.application.dto.TagResponse 클래스에 기반을 둔 엔티티
 * @author Hyeon9mak
 */
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MostPopularStudylogTag extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "most_popular_studylog_id")
    private MostPopularStudylog mostPopularStudylog;
}
