package wooteco.prolog.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
