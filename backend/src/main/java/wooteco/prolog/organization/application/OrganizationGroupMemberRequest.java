package wooteco.prolog.organization.application;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrganizationGroupMemberRequest {

    private String username;
    private String nickname;
}
