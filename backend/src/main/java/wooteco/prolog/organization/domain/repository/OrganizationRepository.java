package wooteco.prolog.organization.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
