package wooteco.prolog.organization.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class OrganizationGroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long organizationGroupId;
    private String username;
    private String nickname;
    private Long memberId;

    public OrganizationGroupMember(Long organizationGroupId, String username, String nickname) {
        this.organizationGroupId = organizationGroupId;
        this.username = username;
        this.nickname = nickname;
    }

    public void updateMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
