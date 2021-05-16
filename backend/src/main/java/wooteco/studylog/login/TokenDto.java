package wooteco.studylog.login;

public class TokenDto {

    private final String code;

    public TokenDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
