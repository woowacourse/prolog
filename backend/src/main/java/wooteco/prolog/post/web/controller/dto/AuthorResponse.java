package wooteco.prolog.post.web.controller.dto;

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
}