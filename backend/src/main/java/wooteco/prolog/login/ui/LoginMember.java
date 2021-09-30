package wooteco.prolog.login.ui;

public class LoginMember {

    public enum Authority {
        ANONYMOUS, MEMBER
    }

    private Long id;
    private Authority authority;

    public LoginMember(Authority authority) {
        this(null, authority);
    }

    public LoginMember(Long id, Authority authority) {
        this.id = id;
        this.authority = authority;
    }

    public boolean isAnonymous() {
        return authority.equals(Authority.ANONYMOUS);
    }

    public Long getId() {
        return id;
    }
}
