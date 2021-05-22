package wooteco.prolog.post.web.controller.dto;

import java.util.Objects;

public class AuthorResponse {
    private Long id;
    private String nickName;
    private String imageUrl;

    public AuthorResponse() {
    }

    public AuthorResponse(Long id, String nickName, String image) {
        this.id = id;
        this.nickName = nickName;
        this.imageUrl = image;
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorResponse that = (AuthorResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(nickName, that.nickName) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, imageUrl);
    }
}