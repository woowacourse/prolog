package wooteco.prolog.studylog.application.dto;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.text.StringEscapeUtils;
import wooteco.prolog.studylog.domain.Studylog;

@AllArgsConstructor
@Getter
public class StudylogRssFeedResponse {

    private final String title;
    private final String content;
    private final String author;
    private final String link;
    private final Date date;

    public static List<StudylogRssFeedResponse> listOf(List<Studylog> studylogs, String url) {
        return studylogs.stream()
            .map(studylog -> StudylogRssFeedResponse.of(studylog, url))
            .collect(toList());
    }

    public static StudylogRssFeedResponse of(Studylog studylog, String url) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        return new StudylogRssFeedResponse(
            StringEscapeUtils.escapeXml10(studylog.getTitle()),
            "",
            StringEscapeUtils.escapeXml10(studylog.getNickname()),
            url + "/studylogs/" + studylog.getId(),
            Date.from(Instant.parse(studylog.getCreatedAt().format(formatter)))
        );
    }
}
