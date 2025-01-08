package wooteco.prolog.organization.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroupSession;

public interface OrganizationGroupSessionRepository extends JpaRepository<OrganizationGroupSession, Long> {

    List<OrganizationGroupSession> findByOrganizationGroupIdIn(List<Long> organizationGroupIds);

    OrganizationGroupSession findByOrganizationGroupId(Long organizationGroupId);
}
