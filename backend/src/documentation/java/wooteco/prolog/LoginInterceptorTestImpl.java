package wooteco.prolog;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wooteco.prolog.login.ui.LoginInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Profile("docu")
@Component
public class LoginInterceptorTestImpl implements LoginInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }
}
