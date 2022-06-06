package wooteco.prolog.membertag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.util.MemberFixture;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;
import wooteco.prolog.studylog.studylog.util.StudylogFixture;
import wooteco.prolog.studylog.studylog.util.StudylogUtilCRUD;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberTagServiceTest {

    @Autowired
    private StudylogUtilCRUD studylogUtilCRUD;
    @Autowired
    private MemberTagService memberTagService;

    @Test
    @DisplayName("멤버가 등록된 태그와 등록된 학습로그 수 확인")
    public void findTagByMember() throws Exception {
        //given
        String tag1 = "자동차";
        String tag2 = "랜덤";
        String tag3 = "객체지향";
        String tag4 = "전략패턴";

        studylogUtilCRUD.등록(StudylogFixture.자동차_미션_정리, MemberFixture.나봄, tag1, tag3, tag4);
        studylogUtilCRUD.등록(StudylogFixture.로또_미션_정리, MemberFixture.나봄, tag2, tag3, tag4);

        //when
        final List<MemberTagResponse> memberTagResponses =
            memberTagService.findByMember(MemberFixture.나봄.getMemberName());

        //then
        assertThat(memberTagResponses)
            .extracting(
                MemberTagResponse::getCount,
                memberTagResponse -> memberTagResponse.getTagResponse().getName()
            )
            .containsExactly(
                tuple(2, "ALL"),
                tuple(2, tag3),
                tuple(2, tag4),
                tuple(1, tag1),
                tuple(1, tag2)
            );
    }
}
