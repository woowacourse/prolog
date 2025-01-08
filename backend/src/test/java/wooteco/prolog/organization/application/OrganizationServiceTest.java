package wooteco.prolog.organization.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.organization.domain.Organization;
import wooteco.prolog.organization.domain.OrganizationGroup;
import wooteco.prolog.organization.domain.OrganizationGroupSession;

@SpringBootTest
@Transactional
class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @Test
    void organization() {
        // 조직 추가
        Organization organization = organizationService.saveOrganization("우아한테크코스");

        // 조직 그룹 추가
        OrganizationGroup organizationGroup =
            organizationService.saveOrganizationGroup(organization.getId(), "우아한테크코스 3기");

        assertThat(organizationGroup.getId()).isNotNull();
        assertThat(organizationGroup.getName()).isEqualTo("우아한테크코스 3기");

        // 조직 그룹 멤버 추가
        organizationService.addOrganizationGroupMember(organizationGroup.getId(), Lists.newArrayList(
            new OrganizationGroupMemberRequest("username1", "nickname1"),
            new OrganizationGroupMemberRequest("username2", "nickname2"),
            new OrganizationGroupMemberRequest("username3", "nickname3")
        ));

        // 조직 그룹 세션 추가
        organizationService.addOrganizationGroupSessions(
            organizationGroup.getId(),
            Lists.newArrayList("레벨1", "레벨2"));

        // 조직 그룹 멤버가 자신의 세션을 조회할 때 조직 그룹 세션의 목록을 조회할 수 있다.
        List<OrganizationGroupSession> organizationGroupSessions =
            organizationService.findOrganizationGroupSessionsByMemberUsername("username2");

        assertThat(organizationGroupSessions).hasSize(2);
        assertThat(organizationGroupSessions.get(0).getSession().getName()).isEqualTo("레벨1");
        assertThat(organizationGroupSessions.get(1).getSession().getName()).isEqualTo("레벨2");
    }
}
