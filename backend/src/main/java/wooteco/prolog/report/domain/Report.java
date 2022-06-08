package wooteco.prolog.report.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long memberId;

    public Report(String title, String description, Long memberId, LocalDate startDate,
                  LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBelongTo(Long memberId) {
        return this.memberId.equals(memberId);
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
