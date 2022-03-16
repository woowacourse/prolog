package wooteco.prolog.studylog.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import wooteco.prolog.studylog.application.RssFeedService;

@Controller
@RequestMapping("/rss")
public class RssFeedController {

    private final RssFeedService rssFeedService;

    public RssFeedController(RssFeedService rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @GetMapping
    public View getRssFeed() {
        return rssFeedService;
    }
}
