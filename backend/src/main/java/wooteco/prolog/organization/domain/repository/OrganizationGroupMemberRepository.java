package wooteco.prolog.organization.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.organization.domain.OrganizationGroupMember;

import java.util.List;

public interface OrganizationGroupMemberRepository extends JpaRepository<OrganizationGroupMember, Long> {

    List<OrganizationGroupMember> findByUsername(String username);

    List<OrganizationGroupMember> findByMemberId(Long memberId);

}
