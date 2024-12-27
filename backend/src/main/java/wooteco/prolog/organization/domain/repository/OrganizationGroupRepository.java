package wooteco.prolog.organization.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroup;

public interface OrganizationGroupRepository extends JpaRepository<OrganizationGroup, Long> {

}
