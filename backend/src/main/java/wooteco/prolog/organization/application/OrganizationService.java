package wooteco.prolog.organization.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberCreatedEvent;
import wooteco.prolog.organization.domain.Organization;
import wooteco.prolog.organization.domain.OrganizationGroup;
import wooteco.prolog.organization.domain.OrganizationGroupMember;
import wooteco.prolog.organization.domain.OrganizationGroupSession;
import wooteco.prolog.organization.domain.repository.OrganizationGroupMemberRepository;
import wooteco.prolog.organization.domain.repository.OrganizationGroupRepository;
import wooteco.prolog.organization.domain.repository.OrganizationGroupSessionRepository;
import wooteco.prolog.organization.domain.repository.OrganizationRepository;
import wooteco.prolog.session.domain.Session;

@Service
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationGroupRepository organizationGroupRepository;
    private final OrganizationGroupMemberRepository organizationGroupMemberRepository;
    private final OrganizationGroupSessionRepository organizationGroupSessionRepository;

    public OrganizationService(OrganizationRepository organizationRepository,
                               OrganizationGroupRepository organizationGroupRepository,
                               OrganizationGroupMemberRepository organizationGroupMemberRepository,
                               OrganizationGroupSessionRepository organizationGroupSessionRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationGroupRepository = organizationGroupRepository;
        this.organizationGroupMemberRepository = organizationGroupMemberRepository;
        this.organizationGroupSessionRepository = organizationGroupSessionRepository;
    }

    @Transactional
    public Organization saveOrganization(String name) {
        return organizationRepository.save(new Organization(name));
    }

    @Transactional
    public OrganizationGroup saveOrganizationGroup(Long organizationId, String name) {
        return organizationGroupRepository.save(new OrganizationGroup(organizationId, name));
    }

    @Transactional
    public void addOrganizationGroupMember(Long organizationGroupId,
                                           List<OrganizationGroupMemberRequest> organizationGroupMemberRequests) {
        List<OrganizationGroupMember> organizationGroupMembers = organizationGroupMemberRequests.stream()
            .map(it -> new OrganizationGroupMember(organizationGroupId, it.getUsername(), it.getNickname()))
            .collect(Collectors.toList());

        organizationGroupMemberRepository.saveAll(organizationGroupMembers);
    }

    @Transactional
    public void addOrganizationGroupSessions(Long organizationGroupId, List<String> sessionNames) {
        List<OrganizationGroupSession> organizationGroupSessions = sessionNames.stream()
            .map(it -> new OrganizationGroupSession(organizationGroupId, new Session(it)))
            .collect(Collectors.toList());

        organizationGroupSessionRepository.saveAll(organizationGroupSessions);
    }

    public List<OrganizationGroupSession> findOrganizationGroupSessionsByMemberUsername(String username) {
        List<OrganizationGroupMember> organizationGroupMembers = organizationGroupMemberRepository.findByUsername(
            username);

        List<Long> organizationGroupIds = organizationGroupMembers.stream()
            .map(OrganizationGroupMember::getOrganizationGroupId)
            .collect(Collectors.toList());

        // organizationGroupIds 로 organizationGroupSession 조회
        return organizationGroupSessionRepository.findByOrganizationGroupIdIn(organizationGroupIds);
    }

    @EventListener
    public void handleMemberCreatedEvent(MemberCreatedEvent event) {
        // OrganizationGroupMember를 username으로 조회
        String username = event.getMember().getUsername();
        List<OrganizationGroupMember> organizationGroupMembers =
            organizationGroupMemberRepository.findByUsername(username);

        // 조회한 OrganizationGroupMember 의 memberId를 변경
        organizationGroupMembers.forEach(it -> it.updateMemberId(event.getMember().getId()));
    }

    public List<OrganizationGroup> findOrganizationGroupsByMemberId(Long memberId) {
        List<Long> organizationGroupIds = organizationGroupMemberRepository.findByMemberId(
            memberId).stream().map(OrganizationGroupMember::getOrganizationGroupId).collect(Collectors.toList());
        return organizationGroupRepository.findByIdInOrderByIdDesc(organizationGroupIds);
    }

    public boolean isMemberOfOrganization(Member member) {
        return organizationGroupMemberRepository.existsByUsername(member.getUsername());
    }
}
