package wooteco.support.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.github.maricn.logback.SlackAppender;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomSlackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private final SlackAppender slackAppender = new SlackAppender();

    @Override
    protected void append(final ILoggingEvent evt) {
        sendMessageToSlack(evt);
    }

    private void sendMessageToSlack(ILoggingEvent evt) {
        try {
            Method append = SlackAppender.class.getDeclaredMethod("append", ILoggingEvent.class);
            append.setAccessible(true);
            append.invoke(slackAppender, evt);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    public String getWebhookUri() {
        return slackAppender.getWebhookUri();
    }

    public void setWebhookUri(String webhookUri) {
        slackAppender.setWebhookUri(webhookUri);
    }

    public String getToken() {
        return slackAppender.getToken();
    }

    public void setToken(String token) {
        slackAppender.setToken(token);
    }

    public String getChannel() {
        return slackAppender.getChannel();
    }

    public void setChannel(String channel) {
        slackAppender.setChannel(channel);
    }

    public String getUsername() {
        return slackAppender.getUsername();
    }

    public void setUsername(String username) {
        slackAppender.setUsername(username);
    }

    public String getIconEmoji() {
        return slackAppender.getIconEmoji();
    }

    public void setIconEmoji(String iconEmoji) {
        slackAppender.setIconEmoji(iconEmoji);
    }

    public String getIconUrl() {
        return slackAppender.getIconEmoji();
    }

    public void setIconUrl(String iconUrl) {
        slackAppender.setIconUrl(iconUrl);
    }

    public Boolean getColorCoding() {
        return slackAppender.getColorCoding();
    }

    public void setColorCoding(Boolean colorCoding) {
        slackAppender.setColorCoding(colorCoding);
    }

    public int getTimeout() {
        return slackAppender.getTimeout();
    }

    public void setTimeout(int timeout) {
        slackAppender.setTimeout(timeout);
    }

    public Layout<ILoggingEvent> getLayout() {
        return slackAppender.getLayout();
    }

    public void setLayout(final Layout<ILoggingEvent> layout) {
        slackAppender.setLayout(layout);
    }
}

