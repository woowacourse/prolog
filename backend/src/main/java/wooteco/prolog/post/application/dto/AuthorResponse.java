package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AuthorResponse {
    private Long id;
    private String nickName;
    private String imageUrl;
}