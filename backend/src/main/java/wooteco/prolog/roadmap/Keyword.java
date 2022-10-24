package wooteco.prolog.roadmap;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import wooteco.prolog.roadmap.exception.KeywordAndKeywordParentSameException;
import wooteco.prolog.roadmap.exception.KeywordSeqException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int seq;

    @Column(nullable = false)
    private int importance;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Keyword parent;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> children = new ArrayList<>();

    @Builder
    public Keyword(final Long id,
                   final String name,
                   final String description,
                   final int seq,
                   final int importance,
                   final Long sessionId,
                   final Keyword parent) {
        validateSeq(seq);
        this.id = id;
        this.name = name;
        this.description = description;
        this.seq = seq;
        this.importance = importance;
        this.sessionId = sessionId;
        this.parent = parent;
    }

    private void validateSeq(final int seq) {
        if (seq <= 0) {
            throw new KeywordSeqException();
        }
    }

    public Keyword(final String name,
                   final String description,
                   final int seq,
                   final int importance,
                   final Long sessionId,
                   final Keyword parent) {
        this(null, name, description, seq, importance, sessionId, parent);
    }

    public static Keyword createKeyword(final String name,
                                        final String description,
                                        final int seq,
                                        final int importance,
                                        final Long sessionId,
                                        final Keyword parent) {
        return Keyword.builder()
            .name(name)
            .description(description)
            .seq(seq)
            .importance(importance)
            .sessionId(sessionId)
            .parent(parent)
            .build();
    }

    public void validateKeywordParent() {
        if (parent != null && id.equals(parent.getId())) {
            throw new KeywordAndKeywordParentSameException();
        }
    }

    public Long getParentIdOrNull() {
        if (parent == null) {
            return null;
        }
        return parent.getId();
    }

    public void update(final String name, final String description, final int seq,
                       final int importance, final Keyword keywordParent) {
        this.name = name;
        this.description = description;
        this.seq = seq;
        this.importance = importance;
        this.parent = keywordParent;
        validateKeywordParent();
    }
}
