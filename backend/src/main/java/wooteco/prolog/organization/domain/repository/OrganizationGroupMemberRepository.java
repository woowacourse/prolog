package wooteco.prolog.organization.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroupMember;

public interface OrganizationGroupMemberRepository extends JpaRepository<OrganizationGroupMember, Long> {

    List<OrganizationGroupMember> findByUsername(String username);

    List<OrganizationGroupMember> findByMemberId(Long memberId);

}
