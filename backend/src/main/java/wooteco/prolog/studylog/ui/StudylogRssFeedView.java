package wooteco.prolog.studylog.ui;

import static java.util.stream.Collectors.toList;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;

@Component
public class StudylogRssFeedView extends AbstractRssFeedView {

    @Value("${application.url}")
    private String url;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
                                     HttpServletRequest request) {
        feed.setTitle("Prolog | 우아한테크코스 학습로그 저장소");
        feed.setDescription("우아한테크코스 크루들이 배운 내용을 기록하는 학습로그 저장소입니다.");
        feed.setLink(url);
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
                                        HttpServletResponse response) {
        List<StudylogRssFeedResponse> rssFeedResponses = (List<StudylogRssFeedResponse>) model.get(
            "rssFeeds");

        return rssFeedResponses.stream()
            .map(this::createItem)
            .collect(toList());
    }

    private Item createItem(StudylogRssFeedResponse rssFeedResponse) {
        Item item = new Item();

        item.setTitle(rssFeedResponse.getTitle());
        item.setLink(rssFeedResponse.getLink());
        item.setPubDate(rssFeedResponse.getDate());
        item.setAuthor(rssFeedResponse.getAuthor());
        item.setDescription(new Description());

        return item;
    }

    private Description createDescription(StudylogRssFeedResponse rssFeedResponse) {
        Description description = new Description();

//        Parser parser = Parser.builder().build();
//        Node document = parser.parse(rssFeedResponse.getContent());
//
//        HtmlRenderer renderer = HtmlRenderer.builder().build();
//        String html = renderer.render(document);
//
//        description.setValue(StringEscapeUtils.escapeXml10(html));

        return description;
    }

    @Override
    protected void setResponseContentType(HttpServletRequest request,
                                          HttpServletResponse response) {
        response.setContentType("application/rss+xml;charset=UTF-8");
    }
}
