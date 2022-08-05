package kr.co.techcourse.prolog.batch.job.sample.chunk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Crew(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return String.format("나는 크루 %s 야!", name);
    }
}
