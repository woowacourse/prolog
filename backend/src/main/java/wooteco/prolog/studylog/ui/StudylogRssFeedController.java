package wooteco.prolog.studylog.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import wooteco.prolog.studylog.application.StudylogRssFeedView;

@Controller
@RequestMapping("/rss")
public class StudylogRssFeedController {

    private final StudylogRssFeedView studylogRssFeedView;

    public StudylogRssFeedController(StudylogRssFeedView studylogRssFeedView) {
        this.studylogRssFeedView = studylogRssFeedView;
    }

    @GetMapping
    public View getRssFeed() {
        return studylogRssFeedView;
    }
}
