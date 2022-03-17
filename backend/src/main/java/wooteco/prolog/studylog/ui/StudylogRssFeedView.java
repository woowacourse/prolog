package wooteco.prolog.studylog.ui;

import static java.util.stream.Collectors.toList;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;

@Component
public class StudylogRssFeedView extends AbstractRssFeedView {

    private final StudylogService studylogService;

    @Value("${studylog.link}")
    private String link;

    public StudylogRssFeedView(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @Override
    protected void buildFeedMetadata(
        Map<String, Object> model,
        Channel feed,
        HttpServletRequest request
    ) {
        feed.setTitle("Prolog | 우아한테크코스 학습로그 저장소");
        feed.setLink(link);
        feed.setDescription("우아한테크코스 크루들이 배운 내용을 기록하는 학습로그 저장소입니다.");
    }

    @Override
    protected List<Item> buildFeedItems(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        List<StudylogRssFeedResponse> rssFeedResponses = (List<StudylogRssFeedResponse>) model
            .get("rssFeeds");

        return rssFeedResponses.stream()
            .map(rssFeedResponse -> {
                Item item = new Item();

                Content content = new Content();
                content.setValue(rssFeedResponse.getContent());

                item.setTitle(rssFeedResponse.getTitle());
                item.setLink(rssFeedResponse.getLink());
                item.setContent(content);
                item.setPubDate(rssFeedResponse.getDate());
                item.setAuthor(rssFeedResponse.getAuthor());

                return item;
            })
            .collect(toList());
    }
}
