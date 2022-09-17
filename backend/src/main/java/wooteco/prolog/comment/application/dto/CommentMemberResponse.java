package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentMemberResponse {

    private Long id;
    private String username;
    private String nickname;
    private String imageUrl;
    private String role;

    public CommentMemberResponse(final Long id,
                                 final String username,
                                 final String nickname,
                                 final String imageUrl,
                                 final String role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.role = role;
    }
}
