package kr.co.techcourse.prolog.batch.job.sample.chunk.east.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Numbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private boolean multipleOfThree;

    protected Numbers() {
    }

    public Numbers(Integer number, boolean multipleOfThree) {
        this.number = number;
        this.multipleOfThree = multipleOfThree;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public boolean isMultipleOfThree() {
        return multipleOfThree;
    }

    public static Numbers from(int number) {
        if (number % 3 == 0) {
            return new Numbers(number, true);
        }
        return new Numbers(number, false);
    }
}
