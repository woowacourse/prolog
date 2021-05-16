package wooteco.studylog.login;

public class TokenRequest {

    private String code;

    public TokenRequest() {
    }

    public TokenRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public TokenDto toTokenDto() {
        return new TokenDto(code);
    }
}
