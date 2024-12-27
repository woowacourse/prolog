package wooteco.prolog.member.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Part part;

    @Enumerated(value = EnumType.STRING)
    private Term term;

    public Department(Long id, Part part, Term term) {
        this.id = id;
        this.part = part;
        this.term = term;
    }

    public Department(Long id, String part, String term) {
        this.id = id;
        this.part = Part.getPartByName(part);
        this.term = Term.getTermByName(term);
    }

    public Part getPart() {
        return part;
    }

}
