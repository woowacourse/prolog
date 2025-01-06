package wooteco.prolog.organization.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
