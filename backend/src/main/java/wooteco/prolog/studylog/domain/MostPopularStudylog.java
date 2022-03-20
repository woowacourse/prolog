package wooteco.prolog.studylog.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MostPopularStudylog extends BaseEntity {

    // TODO: 2022/03/19 현구 - 홈페이지를 클릭할 때마다 매번 조회되는 페이지인데,
    //  엔티티들의 ID만 필드로 가질 경우 조회시마다 계속해서 JOIN을 해야한다는 점이 걱정됨.
    //  화면단에 뿌려줄 raw한 정보들만 필드로 가진 테이블(엔티티)로
    //  완전히 새롭게 태어나는게 좋을거 같다.

    // private Long id;
    @Column(nullable = false)
    private Long studylogId;

    // private MemberResponse author;
    @Embedded
    private MostPopularStudylogAuthor author;

    // TODO: 2022/03/20 현구 - 테이블에 저장되는 이름과 필드변수 이름 고민
    // private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime studylogCreatedAt;

    // private LocalDateTime updatedAt;
    @Column(nullable = false)
    private LocalDateTime studylogUpdatedAt;

    // private MissionResponse mission;
    @Embedded
    private MostPopularStudylogMission mission;

    // private String title;
    @Column(nullable = false)
    private String studylogTitle;

    // private String content;
    @Column(nullable = false)
    private String studylogContent;

    // private boolean scrap;
    @Column(nullable = false)
    private boolean scrap;

    // private boolean read;
    @Column(nullable = false)
    private boolean read;

    // private int viewCount;
    @Column(nullable = false)
    private int viewCount;

    // private boolean liked;
    @Column(nullable = false)
    private boolean liked;

    // private int likesCount;
    @Column(nullable = false)
    private int likesCount;
}
