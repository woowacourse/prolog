package wooteco.prolog.steps;

import io.cucumber.java.en.Given;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Department;
import wooteco.prolog.member.domain.repository.DepartmentMemberRepository;
import wooteco.prolog.member.domain.repository.DepartmentRepository;
import wooteco.prolog.member.domain.repository.MemberRepository;

public class GroupMemberStepDefinitions extends AcceptanceSteps {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMemberRepository departmentMemberRepository;

    public GroupMemberStepDefinitions(MemberRepository memberRepository,
                                      DepartmentRepository departmentRepository,
                                      DepartmentMemberRepository departmentMemberRepository) {
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.departmentMemberRepository = departmentMemberRepository;
    }

    @Given("{string}을 멤버그룹과 그룹멤버에 등록하고")
    public void 그룹멤버를_생성하고(String title) {
        Member member = memberRepository.findById(1L).get();
        Department 프론트엔드 = departmentRepository.save(
            new Department(null, "프론트엔드", "4기"));
        Department 백엔드 = departmentRepository.save(new Department(null, "백엔드", "4기"));
        Department 안드로이드 = departmentRepository.save(
            new Department(null, "안드로이드", "4기"));
        departmentMemberRepository.save(new DepartmentMember(null, member, 백엔드));
        departmentMemberRepository.save(new DepartmentMember(null, member, 프론트엔드));
        departmentMemberRepository.save(new DepartmentMember(null, member, 안드로이드));
    }

}
