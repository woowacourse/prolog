package wooteco.prolog.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class MemberResponse {

    private Long id;
    private String username;
    private String nickname;
    private Role role;
    private String imageUrl;

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getNickname(),
            member.getRole(), member.getImageUrl());
    }
}
