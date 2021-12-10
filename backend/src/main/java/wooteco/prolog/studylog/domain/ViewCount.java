package wooteco.prolog.studylog.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class ViewCount {
    private int views;

    public void increase() {
        this.views++;
    }
}
