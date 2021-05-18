package wooteco.studylog.post.web.controller.dto;

import java.util.Objects;

public class AuthorResponse {
    private Long id;
    private String nickName;
    private String imageUrl;

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
        AuthorResponse authorResponse = (AuthorResponse) o;
        return Objects.equals(id, authorResponse.id) && Objects.equals(nickName, authorResponse.nickName) && Objects.equals(imageUrl, authorResponse.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, imageUrl);
    }
}