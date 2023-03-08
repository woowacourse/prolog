package wooteco.prolog.ability.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityUpdateRequest;
import wooteco.prolog.ability.application.dto.DefaultAbilityCreateRequest;
import wooteco.prolog.ability.application.dto.HierarchyAbilityResponse;
import wooteco.prolog.ability.domain.Ability;
import wooteco.prolog.ability.domain.DefaultAbility;
import wooteco.prolog.ability.domain.repository.AbilityRepository;
import wooteco.prolog.ability.domain.repository.DefaultAbilityRepository;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.ability.exception.AbilityHasChildrenException;
import wooteco.prolog.ability.exception.AbilityNotFoundException;
import wooteco.prolog.ability.exception.DefaultAbilityNotFoundException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AbilityService {

    private static final String COMMON_TYPE = "common";

    private final AbilityRepository abilityRepository;
    private final DefaultAbilityRepository defaultAbilityRepository;
    private final MemberService memberService;

    @Transactional
    public AbilityResponse createAbility(Long memberId, AbilityCreateRequest request) {
        Member member = memberService.findById(memberId);
        Ability ability = extractAbility(member, request);

        return AbilityResponse.of(abilityRepository.save(ability));
    }

    private Ability extractAbility(Member member, AbilityCreateRequest request) {
        List<Ability> abilities = findByMemberId(member.getId());

        String name = request.getName();
        String description = request.getDescription();
        String color = request.getColor();
        Long parentId = request.getParent();

        if (Objects.isNull(parentId)) {
            return extractParentAbility(member, abilities, name, description, color);
        }

        return extractChildAbility(member, abilities, name, description, color, parentId);
    }

    private Ability extractParentAbility(Member member, List<Ability> abilities, String name,
                                         String description, String color) {
        Ability parentAbility = Ability.parent(name, description, color, member);

        parentAbility.validateDuplicateName(abilities);
        parentAbility.validateDuplicateColor(abilities);

        return parentAbility;
    }

    private Ability extractChildAbility(Member member, List<Ability> abilities, String name,
                                        String description, String color, Long parentId) {
        Ability parentAbility = findAbilityById(parentId);
        Ability childAbility = Ability.child(name, description, color, parentAbility, member);

        childAbility.validateDuplicateName(abilities);
        childAbility.validateColorWithParent(abilities, parentAbility);

        return childAbility;
    }

    public List<HierarchyAbilityResponse> findAbilitiesByMemberId(Long memberId) {
        return HierarchyAbilityResponse.listOf(findByMemberId(memberId));
    }

    public List<HierarchyAbilityResponse> findAbilitiesByMemberUsername(String username) {
        Member member = memberService.findByUsername(username);

        return findParentAbilitiesByMemberId(member.getId());
    }

    public List<HierarchyAbilityResponse> findParentAbilitiesByMemberId(Long memberId) {
        List<Ability> parentAbilities = abilityRepository.findByMemberIdAndParentIsNull(memberId);

        return HierarchyAbilityResponse.listOf(parentAbilities);
    }

    public List<HierarchyAbilityResponse> findParentAbilitiesByUsername(String username) {
        Member member = memberService.findByUsername(username);
        List<Ability> parentAbilities = abilityRepository.findByMemberIdAndParentIsNull(
            member.getId());

        return HierarchyAbilityResponse.listOf(parentAbilities);
    }

    public List<AbilityResponse> findFlatAbilitiesByMember(String username) {
        Member member = memberService.findByUsername(username);
        List<Ability> abilities = abilityRepository.findByMemberId(member.getId());

        return AbilityResponse.listOf(abilities);
    }

    @Transactional
    public void updateAbility(Long memberId, Long abilityId, AbilityUpdateRequest request) {
        Ability ability = findAbilityByIdAndMemberId(abilityId, memberId);
        List<Ability> abilities = new ArrayList<>(findByMemberId(memberId));
        // 수정할 대상은 중복 검증을 하지 않는다.
        abilities.remove(ability);

        request.toEntity().validateDuplicateName(abilities);

        if (ability.isParent()) {
            request.toEntity().validateDuplicateColor(abilities, ability);
        }

        ability.update(request.toEntity());
    }

    private List<Ability> findByMemberId(Long memberId) {
        return abilityRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteAbility(Long memberId, Long abilityId) {
        Ability ability = findAbilityByIdAndMemberId(abilityId, memberId);

        if (ability.isParent() && !ability.getChildren().isEmpty()) {
            throw new AbilityHasChildrenException();
        }

        abilityRepository.delete(ability);
    }

    public Ability findAbilityById(Long parentId) {
        return abilityRepository.findById(parentId)
            .orElseThrow(AbilityNotFoundException::new);
    }

    private Ability findAbilityByIdAndMemberId(Long abilityId, Long memberId) {
        return abilityRepository.findByIdAndMemberId(abilityId, memberId)
            .orElseThrow(AbilityNotFoundException::new);
    }

    @Transactional
    public Long createDefaultAbility(DefaultAbilityCreateRequest request) {
        if (request.hasParent()) {
            DefaultAbility parentDefaultAbility = findDefaultAbilityById(request.getParentId());
            DefaultAbility childDefaultAbility = createChildDefaultAbility(request,
                parentDefaultAbility);
            return childDefaultAbility.getId();
        }

        DefaultAbility defaultAbility = createParentDefaultAbility(request);
        return defaultAbility.getId();
    }

    private DefaultAbility createChildDefaultAbility(DefaultAbilityCreateRequest request,
                                                     DefaultAbility parentDefaultAbility) {
        return defaultAbilityRepository.save(new DefaultAbility(
            request.getName(),
            request.getDescription(),
            request.getColor(),
            request.getTemplate(),
            parentDefaultAbility
        ));
    }

    private DefaultAbility findDefaultAbilityById(Long defaultAbilityId) {
        return defaultAbilityRepository.findById(defaultAbilityId)
            .orElseThrow(DefaultAbilityNotFoundException::new);
    }

    private DefaultAbility createParentDefaultAbility(DefaultAbilityCreateRequest request) {
        return defaultAbilityRepository.save(new DefaultAbility(
            request.getName(),
            request.getDescription(),
            request.getColor(),
            request.getTemplate()
        ));
    }

    @Transactional
    public void applyDefaultAbilities(Long memberId, String template) {
        Member member = memberService.findById(memberId);
        List<DefaultAbility> defaultAbilities = findDefaultAbilitiesByTemplate(template);
        Map<DefaultAbility, Ability> parentAbilities = new HashMap<>();

        for (DefaultAbility defaultAbility : defaultAbilities) {
            if (defaultAbility.isParent()) {
                Ability parentAbility = mapToParentAbility(member, defaultAbility);
                parentAbilities.put(defaultAbility, parentAbility);
            } else {
                Ability parentAbility = parentAbilities.get(defaultAbility.getParent());
                mapToChildAbility(member, defaultAbility, parentAbility);
            }
        }
    }

    private List<DefaultAbility> findDefaultAbilitiesByTemplate(String templateType) {
        List<DefaultAbility> defaultAbilities = defaultAbilityRepository.findByTemplateOrTemplate(
            COMMON_TYPE, templateType);

        if (defaultAbilities.isEmpty()) {
            throw new DefaultAbilityNotFoundException();
        }

        return defaultAbilities;
    }

    private Ability mapToParentAbility(Member member, DefaultAbility defaultAbility) {
        Ability parentAbility = extractParentAbility(
            member,
            findByMemberId(member.getId()),
            defaultAbility.getName(),
            defaultAbility.getDescription(),
            defaultAbility.getColor()
        );

        return abilityRepository.save(parentAbility);
    }

    private Ability mapToChildAbility(Member member, DefaultAbility defaultAbility,
                                      Ability parentAbility) {
        Ability childAbility = extractChildAbility(
            member,
            findByMemberId(member.getId()),
            defaultAbility.getName(),
            defaultAbility.getDescription(),
            defaultAbility.getColor(),
            parentAbility.getId()
        );

        return abilityRepository.save(childAbility);
    }

    public Ability findById(Long id) {
        return abilityRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Set<Ability> findByIdIn(Long memberId, List<Long> ids) {
        Set<Ability> abilities = new HashSet<>(abilityRepository.findAllById(ids));
        abilities.stream()
            .filter(it -> !it.isBelongsTo(memberId))
            .findAny()
            .ifPresent(it -> {
                throw new RuntimeException(it.getName() + "는 " + memberId + "의 역량이 아닙니다.");
            });
        return abilities;
    }
}
