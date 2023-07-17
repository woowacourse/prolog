package wooteco.prolog.studylog.domain;

import static wooteco.prolog.common.exception.BadRequestCode.INVALID_LIKE_REQUEST_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.INVALID_UNLIKE_REQUEST_EXCEPTION;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import wooteco.prolog.common.exception.BadRequestException;

@Embeddable
public class Likes {

    @OneToMany(
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
        orphanRemoval = true,
        mappedBy = "studylog"
    )
    private List<Like> values;

    public Likes() {
        this(new ArrayList<>());
    }

    public Likes(List<Like> values) {
        this.values = values;
    }

    public List<Like> getValues() {
        return values;
    }

    public void like(Studylog studylog, Long memberId) {
        validateNewLike(memberId);
        values.add(new Like(studylog, memberId));
    }

    private void validateNewLike(Long id) {
        values.stream()
            .map(Like::getMemberId)
            .filter(memberId -> memberId.equals(id))
            .findAny()
            .ifPresent(memberId -> {
                throw new BadRequestException(INVALID_LIKE_REQUEST_EXCEPTION);
            });
    }

    public void unlike(Studylog studylog, Long memberId) {
        validateExistingLike(memberId);
        values.remove(new Like(studylog, memberId));
    }

    private void validateExistingLike(Long id) {
        if (!likedByMember(id)) {
            throw new BadRequestException(INVALID_UNLIKE_REQUEST_EXCEPTION);
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
