package wooteco.prolog.organization.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroupSession;

import java.util.List;

public interface OrganizationGroupSessionRepository extends JpaRepository<OrganizationGroupSession, Long> {

    List<OrganizationGroupSession> findByOrganizationGroupIdIn(List<Long> organizationGroupIds);

    OrganizationGroupSession findByOrganizationGroupId(Long organizationGroupId);
}
