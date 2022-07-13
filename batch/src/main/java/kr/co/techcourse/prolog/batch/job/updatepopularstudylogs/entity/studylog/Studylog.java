package kr.co.techcourse.prolog.batch.job.updatepopularstudylogs.entity.studylog;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * entity는 테이블과 꼭 완전히 맵핑되지 않아도 괜찮습니다. 일부만 일치해도 동작합니다.
 * 배치에서는 Entity의 모든 비즈니스 로직 및 상태를 필요로 하지 않으므로 최소한의 컬럼과 로직만으로
 * 동작을 수행할 수 있도록 아래와 같이 구현합니다.
 * @author 손너잘
 */
@Table(name = "studylog")
@Entity
public class Studylog {

    private static int POPULAR_SCORE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToMany(fetch = FetchType.LAZY)
    List<Like> likes;

    @Column(name = "views")
    int views;

    public int getPopularScore() {
        return (getLikeCount() * POPULAR_SCORE) + getViewCount();
    }

    private int getLikeCount() {
        return likes.size();
    }

    private int getViewCount() {
        return views;
    }

}
