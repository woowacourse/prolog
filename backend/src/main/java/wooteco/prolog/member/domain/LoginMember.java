package wooteco.prolog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.userdetails.UserDetails;

@AllArgsConstructor
@Getter
public class LoginMember implements UserDetails {

    private Long id;
    private String username;
    private String nickname;
    private Role role;
    private String imageUrl;

    @Override
    public String getUsername() {
        return Long.toString(id);
    }

    public static LoginMember of(Member member) {
        return new LoginMember(
            member.getId(),
            member.getUsername(),
            member.getNickname(),
            member.getRole(),
            member.getImageUrl()
        );
    }
}
