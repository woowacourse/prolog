package wooteco.prolog.studylog.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;
import wooteco.prolog.studylog.exception.InvalidLikeRequestException;
import wooteco.prolog.studylog.exception.InvalidUnlikeRequestException;

@Embeddable
public class Likes {

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "studylog_id")
    @BatchSize(size = 1000)
    private final List<Like> values;

    public Likes() {
        this(new ArrayList<>());
    }

    public Likes(List<Like> values) {
        this.values = values;
    }

    public List<Like> getValues() {
        return values;
    }

    public void like(Long id) {
        validateNewLike(id);
        values.add(new Like(id));
    }

    private void validateNewLike(Long id) {
        values.stream()
            .map(Like::getMemberId)
            .filter(memberId -> memberId.equals(id))
            .findAny()
            .ifPresent(memberId -> {
                throw new InvalidLikeRequestException();
            });
    }

    public void unlike(Long id) {
        validateExistingLike(id);
        values.remove(new Like(id));
    }

    private void validateExistingLike(Long id) {
        if (!likedByMember(id)) {
            throw new InvalidUnlikeRequestException();
        }
    }

    public boolean likedByMember(Long memberId) {
        return values.stream()
            .map(Like::getMemberId)
            .anyMatch(id -> id.equals(memberId));
    }

    public int likeCount() {
        return values.size();
    }
}
