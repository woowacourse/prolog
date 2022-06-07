package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityUpdateRequest;
import wooteco.prolog.ability.application.dto.HierarchyAbilityResponse;
import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.ability.domain.DefaultAbility;
import wooteco.prolog.ability.domain.repository.AbilityRepository;
import wooteco.prolog.ability.domain.repository.DefaultAbilityRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.report.exception.AbilityNotFoundException;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.report.exception.DefaultAbilityNotFoundException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class AbilityServiceTest {

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private DefaultAbilityRepository defaultAbilityRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("brown", "브라운", Role.CREW, 1L, "imageUrl"));
    }

    @DisplayName("자식역량 정보 수정이 정상적으로 수행되는지 확인한다.")
    @Test
    void childAbilityUpdateSuccess() {
        // given
        AbilityCreateRequest parentRequest1 = new AbilityCreateRequest("부모1", "부모설명1", "1", null);
        AbilityCreateRequest parentRequest2 = new AbilityCreateRequest("부모2", "부모설명2", "2", null);
        AbilityCreateRequest parentRequest3 = new AbilityCreateRequest("부모3", "부모설명3", "3", null);
        abilityService.createAbility(member.getId(), parentRequest1);
        abilityService.createAbility(member.getId(), parentRequest2);
        abilityService.createAbility(member.getId(), parentRequest3);

        AbilityCreateRequest childRequest1 = new AbilityCreateRequest("자식1", "자식설명1", "1", 1L);
        AbilityCreateRequest childRequest1_2 = new AbilityCreateRequest("자식1_2", "자식설명1_2", "1",
            1L);
        AbilityCreateRequest childRequest2 = new AbilityCreateRequest("자식2", "자식설명2", "2", 2L);
        AbilityCreateRequest childRequest3 = new AbilityCreateRequest("자식3", "자식설명3", "3", 3L);
        abilityService.createAbility(member.getId(), childRequest1);
        abilityService.createAbility(member.getId(), childRequest1_2);
        abilityService.createAbility(member.getId(), childRequest2);
        abilityService.createAbility(member.getId(), childRequest3);

        // when
        AbilityUpdateRequest request = new AbilityUpdateRequest(4L, "자식1_1", "자식설명1", "1");
        HierarchyAbilityResponse expectedResponse = new HierarchyAbilityResponse(request.getId(),
            request.getName(), request.getDescription(), request.getColor(), false,
            new ArrayList<>());

        abilityService.updateAbility(member.getId(), request.getId(), request);
        List<HierarchyAbilityResponse> abilityResponses = abilityService.findAbilitiesByMemberId(
            member.getId());
        HierarchyAbilityResponse updatedResponse = abilityResponses.stream()
            .filter(response -> response.getId().equals(request.getId()))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);

        // then
        assertThat(updatedResponse).usingRecursiveComparison()
            .isEqualTo(expectedResponse);
    }

    @DisplayName("역량을 생성 할 때 부모역량 ID와 일치하는 역량이 없으면 예외가 발생한다.")
    @Test
    void createAbilityException() {
        // given
        AbilityCreateRequest abilityCreateRequest = new AbilityCreateRequest("zi존브라운123",
            "이견 있습니까?", "이견을 피로 물들이는 붉은 색", Long.MAX_VALUE);

        // when, then
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), abilityCreateRequest))
            .isExactlyInstanceOf(AbilityNotFoundException.class);
    }

    @DisplayName("역량을 생성할 때 기존에 존재하는 역량과 이름이 같은 경우 예외가 발생한다.")
    @Test
    void createAbilityNameDuplicateException() {
        // given
        String name = "zi존브라운123";
        AbilityCreateRequest request1 = new AbilityCreateRequest(name, "이견 있습니까?",
            "이견을 피로 물들이는 붉은 색", null);

        abilityService.createAbility(member.getId(), request1);

        // when, then
        AbilityCreateRequest request2 = new AbilityCreateRequest(name, "없어용", "침묵의 검은 색", null);
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), request2))
            .isExactlyInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("부모 역량을 생성할 때 기존에 존재하는 다른 역량들과 색상이 같은 경우 예외가 발생한다.")
    @Test
    void createParentAbilityColorDuplicateException() {
        // given
        String color = "이견을 피로 물들이는 붉은 색";
        AbilityCreateRequest request1 = new AbilityCreateRequest("zi존브라운123", "이견 있습니까?", color,
            null);

        abilityService.createAbility(member.getId(), request1);

        // when, then
        AbilityCreateRequest request2 = new AbilityCreateRequest("그냥막구현해", "없어용", color, null);
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), request2))
            .isExactlyInstanceOf(AbilityParentColorDuplicateException.class);
    }

    @DisplayName("자식 역량을 생성할 때 부모 역량과 색상이 다른 경우 예외가 발생한다.")
    @Test
    void createChildAbilityColorDifferentException() {
        // given
        AbilityCreateRequest parentAbilityRequest = new AbilityCreateRequest("zi존브라운123",
            "이견 있습니까?", "이견을 피로 물들이는 붉은 색", null);

        abilityService.createAbility(member.getId(), parentAbilityRequest);
        Long parentId = abilityService.findAbilitiesByMemberId(member.getId()).iterator().next()
            .getId();

        // when, then
        AbilityCreateRequest childAbilityRequest = new AbilityCreateRequest("그냥막구현해", "없어용",
            "검은 색 이에용", parentId);
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), childAbilityRequest))
            .isExactlyInstanceOf(AbilityParentChildColorDifferentException.class);
    }

    @DisplayName("부모 역량만을 조회한다. (자식 정보가 포함되지 않는다.)")
    @Test
    void parentAbilities() {
        // given
        Ability parentAbility = abilityRepository.save(
            Ability.parent("메타버스", "폴리곤 덩어리들", "123456", member));
        Ability childAbility = abilityRepository.save(
            Ability.child("마자용", "마자아아아~용", "123456", parentAbility, member));

        HierarchyAbilityResponse expectResponse = new HierarchyAbilityResponse(
            parentAbility.getId(),
            parentAbility.getName(),
            parentAbility.getDescription(),
            parentAbility.getColor(),
            parentAbility.isParent(),
            AbilityResponse.listOf(Collections.singletonList(childAbility))
        );

        // when
        List<HierarchyAbilityResponse> responses = abilityService.findParentAbilitiesByMemberId(
            member.getId());

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0)).usingRecursiveComparison()
            .isEqualTo(expectResponse);
    }

    @DisplayName("역량 정보를 수정할 때 다른 역량과 이름이 중복되는 경우 예외가 발생한다.")
    @Test
    void updateAbilityDuplicateNameException() {
        // given
        AbilityCreateRequest createRequest1 = new AbilityCreateRequest("프로그래밍1", "프로그래밍1 역량입니다.",
            "#111111", null);
        AbilityCreateRequest createRequest2 = new AbilityCreateRequest("프로그래밍2", "프로그래밍2 역량입니다.",
            "#222222", null);
        abilityService.createAbility(member.getId(), createRequest1);
        abilityService.createAbility(member.getId(), createRequest2);

        List<HierarchyAbilityResponse> abilityResponses = abilityService.findParentAbilitiesByMemberId(
            member.getId());
        HierarchyAbilityResponse createdAbilityResponse1 = abilityResponses.get(0);
        HierarchyAbilityResponse createdAbilityResponse2 = abilityResponses.get(1);

        AbilityUpdateRequest request = new AbilityUpdateRequest(createdAbilityResponse1.getId(),
            createdAbilityResponse2.getName(), "완전히 새로운건데요?!", "#222222");

        // when, then
        assertThatThrownBy(
            () -> abilityService.updateAbility(createdAbilityResponse1.getId(), request.getId(),
                request))
            .isExactlyInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("역량 정보를 수정할 때 다른 역량과 색상이 중복되는 경우 예외가 발생한다.")
    @Test
    void updateAbilityDuplicateColorException() {
        // given
        AbilityCreateRequest createRequest1 = new AbilityCreateRequest("프로그래밍1", "프로그래밍1 역량입니다.",
            "#111111", null);
        AbilityCreateRequest createRequest2 = new AbilityCreateRequest("프로그래밍2", "프로그래밍2 역량입니다.",
            "#222222", null);
        abilityService.createAbility(member.getId(), createRequest1);
        abilityService.createAbility(member.getId(), createRequest2);

        List<HierarchyAbilityResponse> abilityResponses = abilityService.findParentAbilitiesByMemberId(
            member.getId());
        HierarchyAbilityResponse createdAbilityResponse1 = abilityResponses.get(0);
        HierarchyAbilityResponse createdAbilityResponse2 = abilityResponses.get(1);

        AbilityUpdateRequest request = new AbilityUpdateRequest(createdAbilityResponse1.getId(),
            "완전히 새로운 이름", "완전히 새로운건데요?!", createdAbilityResponse2.getColor());

        // when, then
        assertThatThrownBy(
            () -> abilityService.updateAbility(createdAbilityResponse1.getId(), request.getId(),
                request))
            .isExactlyInstanceOf(AbilityParentColorDuplicateException.class);
    }

    @DisplayName("역량 정보를 수정할 때 부모 역량의 경우 자식 역량의 색상이 모두 변경된다.")
    @Test
    void updateAbilityParentChangeChildrenColors() {
        // given
        AbilityCreateRequest createParentRequest = new AbilityCreateRequest("부모 프로그래밍",
            "프로그래밍 역량입니다.", "#111111", null);
        abilityService.createAbility(member.getId(), createParentRequest);

        HierarchyAbilityResponse createdParentResponse = abilityService.findParentAbilitiesByMemberId(
            member.getId()).get(0);

        AbilityCreateRequest createChildRequest = new AbilityCreateRequest("자식 프로그래밍",
            "부모 프로그래밍의 자식입니다.", createdParentResponse.getColor(), createdParentResponse.getId());
        abilityService.createAbility(member.getId(), createChildRequest);

        String newColor = "#ffffff";
        AbilityUpdateRequest request = new AbilityUpdateRequest(createdParentResponse.getId(),
            "완전히 새로운 이름", "완전히 새로운건데요?!", newColor);

        // when
        abilityService.updateAbility(member.getId(), request.getId(), request);

        // then
        List<HierarchyAbilityResponse> abilityResponses = abilityService.findAbilitiesByMemberId(
            member.getId());
        HierarchyAbilityResponse updatedParentResponse = abilityResponses.stream()
            .filter(response -> response.getId().equals(createdParentResponse.getId()))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);

        AbilityResponse updatedChildResponse = updatedParentResponse.getChildren().get(0);

        assertThat(updatedParentResponse.getColor()).isEqualTo(newColor);
        assertThat(updatedChildResponse.getColor()).isEqualTo(newColor);
    }

    @DisplayName("역량 정보를 수정할 때 자식 역량의 경우 색상이 변경되지 않는다.")
    @Test
    void updateAbilityChildCanNotChangeColor() {
        // given
        String legacyColor = "#111111";
        AbilityCreateRequest createParentRequest1 = new AbilityCreateRequest("부모 프로그래밍",
            "프로그래밍 역량입니다.", legacyColor, null);
        abilityService.createAbility(member.getId(), createParentRequest1);

        HierarchyAbilityResponse createdParentResponse = abilityService.findParentAbilitiesByMemberId(
            member.getId()).get(0);

        AbilityCreateRequest createChildRequest1 = new AbilityCreateRequest("자식 프로그래밍",
            "부모 프로그래밍의 자식입니다.", createdParentResponse.getColor(), createdParentResponse.getId());
        abilityService.createAbility(member.getId(), createChildRequest1);

        AbilityResponse childAbilityDto = abilityService.findParentAbilitiesByMemberId(
            member.getId()).get(0).getChildren().get(0);

        String newColor = "#ffffff";
        String newName = "완전히 새로운 이름";
        String newDescription = "완전히 새로운건데요?!";
        AbilityUpdateRequest request = new AbilityUpdateRequest(childAbilityDto.getId(), newName,
            newDescription, newColor);

        // when
        abilityService.updateAbility(member.getId(), request.getId(), request);

        // then
        Ability ability = abilityService.findAbilityById(childAbilityDto.getId());
        assertThat(ability.getName()).isEqualTo(newName);
        assertThat(ability.getDescription()).isEqualTo(newDescription);
        assertThat(ability.getColor()).isEqualTo(legacyColor);
    }

    @DisplayName("부모 역량 삭제에 성공하면 조회되지 않는다.")
    @Test
    void deleteParentAbility() {
        // given
        Ability ability = abilityRepository.save(
            Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));

        // when
        assertThat(
            abilityService.findAbilitiesByMemberId(member.getId())).usingRecursiveComparison()
            .isEqualTo(HierarchyAbilityResponse.listOf(Collections.singletonList(ability)));

        abilityService.deleteAbility(member.getId(), ability.getId());

        // then
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).isEmpty();
    }

    @DisplayName("자식 역량 삭제에 성공하면 부모 역량과 관계가 끊어진다.")
    @Test
    void deleteChildAbility() {
        // given
        Ability parentAbility = abilityRepository.save(
            Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));
        Ability childAbility = abilityRepository.save(
            Ability.child("손너잘", "내안으어두미", "이견을 피로 물들이는 붉은 색", parentAbility, member));

        // when
        assertThat(parentAbility.getChildren()).containsExactly(childAbility);

        abilityService.deleteAbility(member.getId(), childAbility.getId());
        List<HierarchyAbilityResponse> abilityResponses = abilityService.findAbilitiesByMemberId(
            member.getId());
        List<Long> abilityIds = abilityResponses.stream().map(HierarchyAbilityResponse::getId)
            .collect(Collectors.toList());

        // then
        assertThat(abilityIds).containsExactly(parentAbility.getId());
    }

    // 스펙이 변경되어 Disabled 처리함
    @Disabled
    @DisplayName("부모 역량 삭제에 성공하면 부모의 자역 역량도 모두 삭제된다.")
    @Test
    void deleteParentAbilityAndChildrenAbilities() {
        // given
        Ability parentAbility = abilityRepository.save(
            Ability.parent("브라운", "파파 브라운", "갈색", member));
        Ability childAbility1 = abilityRepository.save(
            Ability.child("현구막", "썬 현구막", "갈색", parentAbility, member));
        Ability childAbility2 = abilityRepository.save(
            Ability.child("서니", "도터 서니", "갈색", parentAbility, member));

        // when
        assertThat(abilityRepository.findAll()).containsExactly(parentAbility, childAbility1,
            childAbility2);
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).hasSize(3);

        abilityService.deleteAbility(member.getId(), parentAbility.getId());

        // then
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).isEmpty();
        assertThat(abilityRepository.findById(parentAbility.getId())).isNotPresent();
        assertThat(abilityRepository.findById(childAbility1.getId())).isNotPresent();
        assertThat(abilityRepository.findById(childAbility2.getId())).isNotPresent();
    }

    @DisplayName("username을 통해 해당 멤버의 역량 목록을 조회한다.")
    @Test
    void findAbilitiesByMemberUsername() {
        // given
        Ability ability1 = abilityRepository.save(
            Ability.parent("나는ZI존브라운이다.", "이견있나?", "피의 붉은 색", member));
        Ability ability2 = abilityRepository.save(
            Ability.parent("나는킹갓브라운이다.", "이견은 없겠지?", "무지개 색", member));

        // when
        List<HierarchyAbilityResponse> abilityResponses = abilityService.findAbilitiesByMemberUsername(
            member.getUsername());

        // then
        assertThat(abilityResponses).usingRecursiveComparison()
            .isEqualTo(HierarchyAbilityResponse.listOf(Arrays.asList(ability1, ability2)));
    }

    @DisplayName("멤버ID와 과정 선택을 통해 기본 역량을 등록한다.")
    @Test
    void createTemplateAbilities() {
        // given
        defaultAbilityRepository.save(new DefaultAbility("프로그래밍", "프로그래밍 입니다.", "#111111", "be"));
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).isEmpty();

        // when
        abilityService.applyDefaultAbilities(member.getId(), "be");

        // then
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).isNotEmpty();
    }

    @DisplayName("멤버ID와 과정 선택을 통해 기본 역량을 등록할 때 과정을 잘못 입력할 경우 예외가 발생한다.")
    @Test
    void createTemplateAbilitiesException() {
        // given
        Long memberId = member.getId();
        assertThat(abilityService.findAbilitiesByMemberId(memberId)).isEmpty();

        // when, then
        assertThatThrownBy(() -> abilityService.applyDefaultAbilities(memberId, "ce"))
            .isExactlyInstanceOf(DefaultAbilityNotFoundException.class);
    }
}
