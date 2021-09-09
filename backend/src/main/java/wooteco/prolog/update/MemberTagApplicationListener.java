package wooteco.prolog.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.member.domain.repository.MemberTagRepository;
import wooteco.prolog.studylog.application.StudylogTagService;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;

@Component
@RequiredArgsConstructor
public class MemberTagApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final UpdatedContentsRepository updatedContentsRepository;
    private final StudylogTagService studylogTagService;
    private final MemberTagRepository memberTagRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final Optional<UpdatedContents> memberTagUpdated = updatedContentsRepository
            .findByContent(UpdateContent.MEMBER_TAG_UPDATE);

        if(!memberTagUpdated.isPresent() || memberTagUpdated.get().updated()) {
            return;
        }

        updateMemberTags();
        memberTagUpdated.get().update();
    }

    private void updateMemberTags() {
        final ArrayList<MemberTag> memberTags = new ArrayList<>();
        final List<StudylogTag> studylogTags = studylogTagService.findAll();
        for (StudylogTag studylogTag : studylogTags) {
            final Tag tag = studylogTag.getTag();
            final Member member = studylogTag.getStudylog().getMember();
            memberTags.add(new MemberTag(member, tag));
        }

        memberTagRepository.register(memberTags);
    }
}
