package wooteco.prolog.fixtures;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum GithubResponses {
    소롱("1", "access_token_1", "소롱", "soulG", "111111", "https://avatars.githubusercontent.com/u/52682603?v=4"),
    웨지("2", "access_token_2", "웨지", "sihyung92", "222222", "https://avatars.githubusercontent.com/u/51393021?v=4"),
    서니("3", "access_token_3", "서니", "sunhpark42", "333333", "https://avatars.githubusercontent.com/u/67677561?v=4"),
    엘라("4", "access_token_4", "엘라", "HyuuunjuKim", "444444", "https://avatars.githubusercontent.com/u/43339385?v=4"),
    브라운("5", "access_token_5", "브라운", "gracefulBrown", "555555", "https://avatars.githubusercontent.com/u/46308949?v=4");

    private String code;
    private String accessToken;
    private String name;
    private String login;
    private String id;
    private String avatarUrl;

    GithubResponses(String code, String accessToken, String name, String login, String id, String avatarUrl) {
        this.code = code;
        this.accessToken = accessToken;
        this.name = name;
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
    }

    public String getCode() {
        return code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public static GithubResponses findByName(String member) {
        return Arrays.stream(values())
                .filter(it -> StringUtils.equals(it.name, member))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static GithubResponses findByCode(String code) {
        return Arrays.stream(values())
                .filter(it -> StringUtils.equals(it.code, code))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static GithubResponses findByToken(String accessToken) {
        return Arrays.stream(values())
                .filter(it -> StringUtils.equals(it.accessToken, accessToken))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}