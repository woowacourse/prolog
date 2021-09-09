package wooteco.prolog.update;

public enum UpdateContent {
    MEMBER_TAG_UPDATE("기존에 존재하는 포스트 태그를 멤버 태그로 업데이트");

    private final String message;

    UpdateContent(String message) {
        this.message = message;
    }
}
