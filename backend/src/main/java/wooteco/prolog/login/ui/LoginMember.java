package wooteco.prolog.login.ui;

import java.util.function.Function;
import java.util.function.Supplier;

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
        return Authority.ANONYMOUS.equals(authority);
    }

    public boolean isMember() {
        return Authority.MEMBER.equals(authority);
    }

    public Long getId() {
        return id;
    }

    public UserAction act() {
        return new UserAction(this);
    }

    public static class UserAction {

        private final LoginMember loginMember;
        private Object returnValue;

        public UserAction(LoginMember loginMember) {
            this.loginMember = loginMember;
        }

        public UserAction ifAnonymous(Supplier<?> supplier) {
            if (loginMember.isAnonymous()) {
                this.returnValue = supplier.get();
            }
            return this;
        }

        public <T extends Throwable> UserAction throwIfAnonymous(Supplier<? extends T> supplier)
            throws T {
            if (loginMember.isAnonymous()) {
                throw supplier.get();
            }
            return this;
        }

        public UserAction ifMember(Function<Long, ?> function) {
            if (loginMember.isMember()) {
                this.returnValue = function.apply(loginMember.getId());
            }
            return this;
        }

        public Object getReturnValue() {
            return returnValue;
        }

        @SuppressWarnings("unchecked")
        public <T> T getReturnValue(Class<T> returnType) {
            return (T) returnValue;
        }
    }
}
