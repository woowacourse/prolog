package wooteco.support.security.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FilterInvocation {

    private HttpServletRequest request;
    private HttpServletResponse response;
}
