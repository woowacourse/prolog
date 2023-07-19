package wooteco.prolog.steps;

import io.cucumber.java.en.Given;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.repository.GroupMemberRepository;
import wooteco.prolog.member.domain.repository.MemberGroupRepository;
import wooteco.prolog.member.domain.repository.MemberRepository;

public class GroupMemberStepDefinitions extends AcceptanceSteps {

    private final MemberRepository memberRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupMemberStepDefinitions(MemberRepository memberRepository,
                                      MemberGroupRepository memberGroupRepository,
                                      GroupMemberRepository groupMemberRepository) {
        this.memberRepository = memberRepository;
        this.memberGroupRepository = memberGroupRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    @Given("{string}을 멤버그룹과 그룹멤버에 등록하고")
    public void 그룹멤버를_생성하고(String title) {
        Member member = memberRepository.findById(1L).get();
        MemberGroup 프론트엔드 = memberGroupRepository.save(
            new MemberGroup(null, "4기 프론트엔드", "4기 프론트엔드 설명"));
        MemberGroup 백엔드 = memberGroupRepository.save(new MemberGroup(null, "4기 백엔드", "4기 백엔드 설명"));
        MemberGroup 안드로이드 = memberGroupRepository.save(
            new MemberGroup(null, "4기 안드로이드", "4기 안드로이드 설명"));
        groupMemberRepository.save(new GroupMember(null, member, 백엔드));
        groupMemberRepository.save(new GroupMember(null, member, 프론트엔드));
        groupMemberRepository.save(new GroupMember(null, member, 안드로이드));
    }
}
