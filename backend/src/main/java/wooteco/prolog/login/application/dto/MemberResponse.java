package wooteco.prolog.login.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberResponse {

    private Long id;
    private String nickname;
    private Role role;
    private String imageUrl;

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getNickname(), member.getRole(), member.getImageUrl());
    }
}
