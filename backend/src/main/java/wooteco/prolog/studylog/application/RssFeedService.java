package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import wooteco.prolog.studylog.application.dto.RssFeedResponse;

@Service
public class RssFeedService extends AbstractRssFeedView {

    private final StudylogService studylogService;

    @Value("${studylog.url}")
    private String url;

    public RssFeedService(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @Override
    protected void buildFeedMetadata(
        Map<String, Object> model,
        Channel feed,
        HttpServletRequest request
    ) {
        feed.setTitle("Prolog | 우아한테크코스 학습로그 저장소");
        feed.setLink("https://prolog.techcourse.co.kr/");
        feed.setDescription("우아한테크코스 크루들이 배운 내용을 기록하는 학습로그 저장소입니다.");
    }

    @Override
    protected List<Item> buildFeedItems(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        List<RssFeedResponse> rssFeedResponses = studylogService.readRssFeeds(url);

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
