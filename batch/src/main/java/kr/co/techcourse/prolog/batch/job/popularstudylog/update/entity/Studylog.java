package kr.co.techcourse.prolog.batch.job.popularstudylog.update.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Studylog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Studylog() {
    }

    public Long getId() {
        return id;
    }
}
