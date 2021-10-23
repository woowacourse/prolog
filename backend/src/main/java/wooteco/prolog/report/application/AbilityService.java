package wooteco.prolog.report.application;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.exception.AbilityCsvException;
import wooteco.prolog.report.exception.AbilityNotFoundException;

@Service
@Transactional(readOnly = true)
public class AbilityService {

    private static final int NAME = 0;
    private static final int DESCRIPTION = 1;
    private static final int COLOR = 2;
    private static final int PARENT_ABILITY = 3;
    private static final int CHILD_ABILITY = 4;

    private final AbilityRepository abilityRepository;
    private final MemberService memberService;

    public AbilityService(AbilityRepository abilityRepository, MemberService memberService) {
        this.abilityRepository = abilityRepository;
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
    public void createTemplateAbilities(Long memberId, String template) {
        int testCount = 0;
        Member member = memberService.findById(memberId);
        URL url = ClassLoader.getSystemResource(String.format("static/%s-default-abilities.csv", template));
        testCount = 1;
        try (
            FileReader fileReader = new FileReader(url.getFile());
            CSVReader reader = new CSVReader(fileReader)
        ) {
            List<Ability> abilities = new ArrayList<>();

            testCount = 2;
            String[] line;
            while ((line = reader.readNext()) != null) {
                testCount = 3;
                String[] splitLine = line[0].split("\\|");
                testCount = 4;
                saveParentOrChildAbility(member, abilities, splitLine);
                testCount = 5;
            }
        } catch (IOException e) {
            throw new AbilityCsvException(String.format("진짜제발 됐으면 좋겠다\n--------------------------\n%s\n%d", url.getFile(), testCount));
        } catch (NullPointerException e) {
            throw new AbilityCsvException(String.format("이번엔 되면 안될까? 제발\n--------------------------\n%s\n%d", url.getFile(), testCount));
        } catch (CsvValidationException e) {
            throw new AbilityCsvException(String.format("춥고.. 배고프고.. 졸려..\n--------------------------\n%s\n%d", url.getFile(), testCount));
        }
    }

    private void saveParentOrChildAbility(Member member, List<Ability> abilities, String[] splitLine) {
        if (splitLine.length == PARENT_ABILITY) {
            Ability parentAbility = extractParentAbility(member, new ArrayList<>(abilities),
                splitLine[NAME], splitLine[DESCRIPTION], splitLine[COLOR]);

            abilities.add(abilityRepository.save(parentAbility));
        }

        if (splitLine.length == CHILD_ABILITY){
            Ability parentAbility = matchedNameAbility(abilities, splitLine[PARENT_ABILITY]);
            Ability childAbility = extractChildAbility(member, new ArrayList<>(abilities),
                splitLine[NAME], splitLine[DESCRIPTION], splitLine[COLOR], parentAbility.getId());

            abilities.add(abilityRepository.save(childAbility));
        }
    }

    private Ability matchedNameAbility(List<Ability> abilities, String abilityName) {
        return abilities.stream()
            .filter(ability -> abilityName.equals(ability.getName()))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }
}
