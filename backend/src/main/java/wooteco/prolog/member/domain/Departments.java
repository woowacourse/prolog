package wooteco.prolog.member.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Departments {

    private List<Department> values;

    public boolean isContainsDepartments(Department department) {
        return values.contains(department);
    }
}
