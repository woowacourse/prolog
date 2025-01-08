package wooteco.prolog.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
