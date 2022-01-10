package wooteco.prolog.report.domain.ablity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "abilities")
public class AbilitiesTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    private String value;

    protected AbilitiesTable() {
    }

    public AbilitiesTable(String value) {
        this(null, null, value);
    }

    public AbilitiesTable(Long id, String value) {
        this(id, null, value);
    }

    public AbilitiesTable(Long id, LocalDateTime createdAt, String value) {
        this.id = id;
        this.createdAt = createdAt;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getValue() {
        return value;
    }
}
