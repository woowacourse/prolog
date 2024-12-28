package wooteco.prolog.member.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.organization.domain.OrganizationGroup;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProfileResponse {

    private Long id;
    private String username;
    private String nickname;
    private String imageUrl;
    private String rssFeedUrl;
    private List<String> organizationGroups;

    public static ProfileResponse of(Member member, List<OrganizationGroup> organizationGroups) {
        List<String> organizationGroupNames = organizationGroups.stream().map(OrganizationGroup::getName).collect(
            Collectors.toList());

        return new ProfileResponse(member.getId(), member.getUsername(), member.getNickname(),
            member.getImageUrl(), member.getRssFeedUrl(), organizationGroupNames);
    }
}
