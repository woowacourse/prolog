package wooteco.prolog.organization.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrganizationGroupSessionRequest {

    private Long organizationGroupId;
    private String name;
}
