package wooteco.prolog.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberUpdateRequest {
    private String username;
    private String nickname;
    private String imageUrl;
}
