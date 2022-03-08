package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import wooteco.prolog.studylog.application.dto.RssFeedResponse;

@Service
@Transactional(readOnly = true)
public class RssFeedService extends AbstractRssFeedView {

    private final StudylogService studylogService;

    public RssFeedService(StudylogService studylogService) {
        this.studylogService = studylogService;
    }

    @Override
    protected void buildFeedMetadata(
        Map<String, Object> model,
        Channel feed,
        HttpServletRequest request
    ) {
        feed.setTitle("PROLOG");
        feed.setDescription("내가 안다고 생각한게 진짜 아는걸까?🧐\n학습로그를 작성하며 메타인지를 경험해보세요!");
        feed.setLink("https://prolog.techcourse.co.kr/");
    }

    @Override
    protected List<Item> buildFeedItems(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        List<RssFeedResponse> rssFeedResponses = studylogService.readRssFeeds();

        return rssFeedResponses.stream()
            .map(rssFeedResponse -> {
                Item item = new Item();

                Content content = new Content();
                content.setValue(rssFeedResponse.getContent());

                item.setTitle(rssFeedResponse.getTitle());
                item.setContent(content);
                item.setAuthor(rssFeedResponse.getAuthor());
                item.setLink(rssFeedResponse.getLink());
                item.setPubDate(rssFeedResponse.getDate());

                return item;
            })
            .collect(toList());
    }
}
