package kr.co.techcourse.prolog.batch.job.sample.chunk.eve.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eve_number")
public class Number {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberValue;

    private boolean multipleOfThree;

    protected Number() {
    }

    private Number(int value, boolean multipleOfThree) {
        this.numberValue = value;
        this.multipleOfThree = multipleOfThree;
    }

    public static Number from(Integer item) {
        return new Number(item, isMultipleOfThree(item));
    }

    private static boolean isMultipleOfThree(Integer item) {
        return item % 3 == 0;
    }

    public int getNumberValue() {
        return numberValue;
    }
}
