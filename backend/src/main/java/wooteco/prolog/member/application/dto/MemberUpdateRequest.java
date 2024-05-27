package wooteco.prolog.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberUpdateRequest {

    private String nickname;
    private String imageUrl;
    private String rssFeedUrl;

    public MemberUpdateRequest(String nickname, String imageUrl) {
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
