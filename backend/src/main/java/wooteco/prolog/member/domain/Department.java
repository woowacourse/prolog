package wooteco.prolog.member.domain;

import static wooteco.prolog.common.exception.BadRequestCode.CANT_FIND_GROUP_TYPE;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestException;

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

    public Department(Long id, String part, String term) {
        this.id = id;
        this.part = Part.valueOf(part);
        this.term = Term.valueOf(term);
    }

    public Part getPart() {
        for (Part part : Part.values()) {
            if (part.isContainedBy(this.part.getName())) {
                return part;
            }
        }
        throw new BadRequestException(CANT_FIND_GROUP_TYPE);
    }
}
