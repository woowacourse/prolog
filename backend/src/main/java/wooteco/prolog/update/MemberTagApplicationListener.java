package wooteco.prolog.update;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

        if (memberTagUpdated.isPresent() && memberTagUpdated.get().updated()) {
            return;
        }

        updateMemberTags();
        updatedContentsRepository.save(new UpdatedContents(null, UpdateContent.MEMBER_TAG_UPDATE, 1));
    }

    private void updateMemberTags() {
        final List<StudylogTag> studylogTags = studylogTagService.findAll();
        for (StudylogTag studylogTag : studylogTags) {
            final Tag tag = studylogTag.getTag();
            final Member member = studylogTag.getStudylog().getMember();
            memberTagRepository.register(Collections.singletonList(new MemberTag(member, tag)));
        }
    }
}
