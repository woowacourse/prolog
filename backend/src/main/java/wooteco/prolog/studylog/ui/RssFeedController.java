package wooteco.prolog.studylog.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import wooteco.prolog.studylog.application.RssFeedService;

@RestController
@RequestMapping("/api")
public class RssFeedController {

    private final RssFeedService rssFeedService;

    public RssFeedController(RssFeedService rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @GetMapping("/rss-feed")
    public View getRssFeed() {
        return rssFeedService;
    }
}
