package wooteco.prolog.organization.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroup;

public interface OrganizationGroupRepository extends JpaRepository<OrganizationGroup, Long> {

    List<OrganizationGroup> findByIdInOrderByIdDesc(List<Long> organizationGroupIds);
}
