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

    public CommentMemberResponse(Long id, String username, String nickname, String imageUrl,
                                 String role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.role = role;
    }
}
