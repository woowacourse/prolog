package wooteco.prolog.report.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.report.application.dto.ability.DefaultAbilityCreateRequest;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.ablity.DefaultAbility;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.domain.ablity.repository.DefaultAbilityRepository;
import wooteco.prolog.report.exception.DefaultAbilityNotFoundException;
import wooteco.prolog.report.exception.AbilityNotFoundException;

@Service
@Transactional(readOnly = true)
public class AbilityService {

    private static final String COMMON_TYPE = "common";

    private final AbilityRepository abilityRepository;
    private final DefaultAbilityRepository defaultAbilityRepository;
    private final MemberService memberService;

    public AbilityService(
        AbilityRepository abilityRepository,
        DefaultAbilityRepository defaultAbilityRepository,
        MemberService memberService
    ) {
        this.abilityRepository = abilityRepository;
        this.defaultAbilityRepository = defaultAbilityRepository;
        this.memberService = memberService;
    }

    @Transactional
    public void createAbility(Long memberId, AbilityCreateRequest request) {
        Member member = memberService.findById(memberId);
        Ability ability = extractAbility(member, request);

        abilityRepository.save(ability);
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

    private Ability extractParentAbility(Member member, List<Ability> abilities, String name, String description,
                                         String color) {
        Ability parentAbility = Ability.parent(name, description, color, member);

        parentAbility.validateDuplicateName(abilities);
        parentAbility.validateDuplicateColor(abilities);

        return parentAbility;
    }

    private Ability extractChildAbility(Member member, List<Ability> abilities, String name, String description,
                                        String color, Long parentId) {
        Ability parentAbility = findAbilityById(parentId);
        Ability childAbility = Ability.child(name, description, color, parentAbility, member);

        childAbility.validateDuplicateName(abilities);
        childAbility.validateColorWithParent(abilities, parentAbility);

        return childAbility;
    }

    public List<AbilityResponse> findAbilitiesByMemberId(Long memberId) {
        return AbilityResponse.of(findByMemberId(memberId));
    }

    public List<AbilityResponse> findAbilitiesByMemberUsername(String username) {
        Member member = memberService.findByUsername(username);

        return AbilityResponse.of(findByMemberId(member.getId()));
    }

    public List<AbilityResponse> findParentAbilitiesByMemberId(Long memberId) {
        List<Ability> parentAbilities = abilityRepository.findByMemberIdAndParentIsNull(memberId);

        return AbilityResponse.of(parentAbilities);
    }

    @Transactional
    public List<AbilityResponse> updateAbility(Long memberId, AbilityUpdateRequest request) {
        Ability legacyAbility = findAbilityByIdAndMemberId(request.getId(), memberId);
        Ability updateAbility = request.toEntity();
        List<Ability> abilities = findByMemberId(memberId);

        legacyAbility.updateWithValidation(updateAbility, abilities);

        return findAbilitiesByMemberId(memberId);
    }

    private List<Ability> findByMemberId(Long memberId) {
        return abilityRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteAbility(Long memberId, Long abilityId) {
        Ability ability = findAbilityByIdAndMemberId(abilityId, memberId);

        abilityRepository.delete(ability);
    }

    private Ability findAbilityById(Long parentId) {
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
            DefaultAbility childDefaultAbility = createChildDefaultAbility(request, parentDefaultAbility);
            return childDefaultAbility.getId();
        }

        DefaultAbility defaultAbility = createParentDefaultAbility(request);
        return defaultAbility.getId();
    }

    private DefaultAbility createChildDefaultAbility(DefaultAbilityCreateRequest request, DefaultAbility parentDefaultAbility) {
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
    public void addDefaultAbilities(Long memberId, String template) {
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
        List<DefaultAbility> defaultAbilities = defaultAbilityRepository
            .findByTemplateOrTemplate(COMMON_TYPE, templateType);

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

    private Ability mapToChildAbility(Member member, DefaultAbility defaultAbility, Ability parentAbility) {
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
}
