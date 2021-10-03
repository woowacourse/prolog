package wooteco.prolog.studylog.studylog.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.member.util.MemberUtilCRUD;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;

@Component
public class StudylogUtilCRUD {

    @Autowired
    private StudylogService studylogService;
    @Autowired
    private MemberUtilCRUD memberUtilCRUD;

    public void 등록(StudylogFixture studylogFixture, MemberFixture member) {
        final Member insertMember = memberUtilCRUD.등록(member);
        studylogService.insertStudylogs(insertMember.getId(), Collections.singletonList(
            studylogFixture.asRequest()));
    }

    public void 등록(MemberFixture memberFixture, String title, String content, Long missionId, String ... tagNames) {
        final List<TagRequest> tagRequests = Arrays.stream(tagNames)
                .map(TagRequest::new)
                .collect(Collectors.toList());

        studylogService.insertStudylogs(memberUtilCRUD.등록(memberFixture).getId(), Collections.singletonList(new StudylogRequest(title, content, missionId, tagRequests)));
    }

    public void 등록(StudylogFixture studylogFixture, MemberFixture memberFixture, String ... tagNames) {
        final List<TagRequest> tagRequests = Arrays.stream(tagNames)
                .map(TagRequest::new)
                .collect(Collectors.toList());

        studylogService.insertStudylogs(memberUtilCRUD.등록(memberFixture).getId(), Collections.singletonList(
            studylogFixture.asRequestWithTags(tagRequests)));
    }

    public void 다중등록(StudylogFixture studylogFixture, MemberFixture member) {
        studylogService.insertStudylogs(memberUtilCRUD.등록(member), Collections.singletonList(
            studylogFixture.asRequest()));
    }
}
