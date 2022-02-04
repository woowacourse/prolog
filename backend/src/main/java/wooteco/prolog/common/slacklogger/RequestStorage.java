package wooteco.prolog.common.slacklogger;

import org.springframework.web.util.ContentCachingRequestWrapper;

public class RequestStorage {

    private ContentCachingRequestWrapper request;

    public void set(ContentCachingRequestWrapper request) {
        this.request = request;
    }

    public ContentCachingRequestWrapper get() {
        return request;
    }
}
