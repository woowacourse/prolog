package wooteco.prolog.organization.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroup;

import java.util.List;

public interface OrganizationGroupRepository extends JpaRepository<OrganizationGroup, Long> {

    List<OrganizationGroup> findByIdInOrderByIdDesc(List<Long> organizationGroupIds);
}
