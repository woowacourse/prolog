package wooteco.prolog.studylog.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;

import java.util.List;

@Controller
@RequestMapping("/rss")
public class StudylogRssFeedController {

    private final StudylogService studylogService;
    private final StudylogRssFeedView studylogRssFeedView;

    @Value("${application.url}")
    private String url;

    public StudylogRssFeedController(StudylogService studylogService,
                                     StudylogRssFeedView studylogRssFeedView) {
        this.studylogService = studylogService;
        this.studylogRssFeedView = studylogRssFeedView;
    }

    @GetMapping
    public View getRssFeed(Model model) {
        List<StudylogRssFeedResponse> rssFeeds = studylogService.readRssFeeds(url);

        model.addAttribute("rssFeeds", rssFeeds);

        return studylogRssFeedView;
    }
}
