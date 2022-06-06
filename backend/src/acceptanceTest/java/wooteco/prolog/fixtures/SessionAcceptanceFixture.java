package wooteco.prolog.fixtures;

import java.util.Arrays;
import java.util.List;
import wooteco.prolog.session.application.dto.SessionRequest;

public class SessionAcceptanceFixture {

    public static SessionRequest session1 = new SessionRequest("세션1");
    public static SessionRequest session2 = new SessionRequest("세션2");
    public static SessionRequest session3 = new SessionRequest("세션3");
    public static SessionRequest session4 = new SessionRequest("세션4");
    public static SessionRequest session5 = new SessionRequest("세션5");
    public static SessionRequest session6 = new SessionRequest("세션6");
    public static SessionRequest session7 = new SessionRequest("세션7");
    public static SessionRequest session8 = new SessionRequest("세션8");
    public static SessionRequest session9 = new SessionRequest("세션9");
    public static SessionRequest session10 = new SessionRequest("세션10");
    public static SessionRequest session11 = new SessionRequest("세션11");

    public static List<SessionRequest> values() {
        return Arrays.asList(session1, session2, session3, session4, session5, session6, session7,
                             session8, session9, session10, session11);
    }
}
