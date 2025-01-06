package wooteco.prolog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Departments {

    private List<Department> values;

    public boolean isContainsDepartments(Department department) {
        return values.contains(department);
    }
}
