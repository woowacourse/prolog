package wooteco.prolog.article.application;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ButtonElement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.article.domain.Article;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SlackService {

    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    @Value("${slack.article.token}")
    private String slackToken;

    @Value("${slack.article.channel}")
    private String slackChannel;

    public void sendSlackMessage(Article article) {
        Slack slack = Slack.getInstance();

        // Image block
        ImageBlock imageBlock = ImageBlock.builder()
            .imageUrl(article.getImageUrl().getUrl())
            .altText("article image")
            .build();

        // Title section
        SectionBlock titleSection = SectionBlock.builder()
            .text(MarkdownTextObject.builder()
                .text("*" + article.getTitle().getTitle() + "*")
                .build())
            .build();

        // Context block
        String name = article.getMember().getNickname() + "(" + article.getMember().getUsername() + ")";
        String date = article.getPublishedAt().toLocalDate().toString();
        ContextBlock contextBlock = ContextBlock.builder()
            .elements(Collections.singletonList(
                MarkdownTextObject.builder()
                    .text(name + " | " + date)
                    .build()))
            .build();

        // Summary section
        SectionBlock summarySection = SectionBlock.builder()
            .text(MarkdownTextObject.builder()
                .text(article.getDescription().getDescription())
                .build())
            .build();

        // Button block
        ButtonElement buttonElement = ButtonElement.builder()
            .text(PlainTextObject.builder()
                .text("자세히 보기")
                .build())
            .url(article.getUrl().getUrl())
            .actionId("button_to_article")
            .build();

        ActionsBlock actionsBlock = ActionsBlock.builder()
            .elements(Arrays.asList(buttonElement))
            .build();

        // Divider block
        DividerBlock dividerBlock = DividerBlock.builder().build();

        // Prologue section
        SectionBlock prologueSection = SectionBlock.builder()
            .text(MarkdownTextObject.builder()
                .text("📝 프롤로그 프로필 페이지에서 `RSS Link` 를 설정해보세요.")
                .build())
            .accessory(ButtonElement.builder()
                .text(PlainTextObject.builder()
                    .text("프롤로그 둘러보기")
                    .emoji(true)
                    .build())
                .value("click_to_prolog")
                .url("https://prolog.techcourse.co.kr/article")
                .actionId("button-action")
                .build())
            .build();

        // List of blocks
        List<LayoutBlock> blocks = Arrays.asList(
            imageBlock,
            titleSection,
            contextBlock,
            summarySection,
            actionsBlock,
            dividerBlock,
            prologueSection,
            dividerBlock
        );

        // Create a message request
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel(slackChannel)
            .text("새로운 글이 등록되었습니다.")
            .blocks(blocks)
            .build();

        try {
            ChatPostMessageResponse chatPostMessageResponse = slack.methods(slackToken).chatPostMessage(request);
            chatPostMessageResponse.getMessage();
            chatPostMessageResponse.getChannel();
        } catch (IOException | SlackApiException e) {
            logger.error(e.getMessage());
        }
    }
}
