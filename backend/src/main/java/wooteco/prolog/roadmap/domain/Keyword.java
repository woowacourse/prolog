package wooteco.prolog.roadmap.domain;


import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_AND_KEYWORD_PARENT_SAME_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_SEQUENCE_EXCEPTION;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import wooteco.prolog.common.exception.BadRequestException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "keyword_reference")
    @Column(name = "url")
    private List<String> references;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Keyword parent;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Keyword> children = new HashSet<>();

    public Keyword(final Long id, final String name, final String description, final int seq,
                   final int importance,
                   final Long sessionId, final Keyword parent, final Set<Keyword> children) {
        validateSeq(seq);
        this.id = id;
        this.name = name;
        this.description = description;
        this.seq = seq;
        this.importance = importance;
        this.sessionId = sessionId;
        this.parent = parent;
        this.children = children;
    }

    public static Keyword createKeyword(final String name,
                                        final String description,
                                        final int seq,
                                        final int importance,
                                        final Long sessionId,
                                        final Keyword parent) {
        return new Keyword(null, name, description, seq, importance, sessionId, parent, null);
    }

    public void update(final String name, final String description, final int seq,
                       final int importance, final Keyword keywordParent) {
        validateSeq(seq); // seq 가 0 보다 작으면 예외를 발생 시킨다.
        validateKeywordParent(keywordParent); //
        this.name = name;
        this.description = description;
        this.seq = seq;
        this.importance = importance;
        this.parent = keywordParent;
    }

    private void validateSeq(final int seq) {
        if (seq <= 0) {
            throw new BadRequestException(ROADMAP_KEYWORD_SEQUENCE_EXCEPTION);
        }
    }

    private void validateKeywordParent(final Keyword parentKeyword) {
        if (this.parent != null && this.id.equals(parentKeyword.getId())) {
            throw new BadRequestException(ROADMAP_KEYWORD_AND_KEYWORD_PARENT_SAME_EXCEPTION);
        }
    }

    public Long getParentIdOrNull() {
        if (parent == null) {
            return null;
        }
        return parent.getId();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Keyword)) {
            return false;
        }
        Keyword keyword = (Keyword) o;
        return Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
