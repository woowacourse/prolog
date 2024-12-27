package wooteco.prolog.organization.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Session;

@Entity
@NoArgsConstructor
@Getter
public class OrganizationGroupSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long organizationGroupId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "session_id")
    private Session session;

    public OrganizationGroupSession(Long organizationGroupId, Session session) {
        this.organizationGroupId = organizationGroupId;
        this.session = session;
    }
}
