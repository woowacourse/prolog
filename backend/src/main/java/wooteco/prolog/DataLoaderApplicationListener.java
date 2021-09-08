package wooteco.prolog;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import wooteco.prolog.level.application.LevelService;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.studylog.application.dto.TagRequest;

import java.util.Arrays;
import java.util.List;

@Profile("local")
@AllArgsConstructor
@Configuration
public class DataLoaderApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private LevelService levelService;
    private MissionService missionService;
    private TagService tagService;
    private MemberService memberService;
    private StudylogService studylogService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // level init
        LevelResponse level1 = levelService.create(new LevelRequest("백엔드Java 레벨1 - 2021"));
        LevelResponse level2 = levelService.create(new LevelRequest("프론트엔드JS 레벨1 - 2021"));
        LevelResponse level3 = levelService.create(new LevelRequest("백엔드Java 레벨2 - 2021"));
        LevelResponse level4 = levelService.create(new LevelRequest("프론트엔드JS 레벨2 - 2021"));

        // mission init
        MissionResponse mission1 = missionService.create(new MissionRequest("자동차경주", level1.getId()));
        MissionResponse mission2 = missionService.create(new MissionRequest("로또", level2.getId()));
        MissionResponse mission3 = missionService.create(new MissionRequest("장바구니", level3.getId()));
        MissionResponse mission4 = missionService.create(new MissionRequest("지하철", level4.getId()));

        // filter init
        List<TagRequest> tagRequests = Arrays.asList(
                new TagRequest("자바"),
                new TagRequest("자바스크립트"),
                new TagRequest("스프링"),
                new TagRequest("리액트")
        );
        tagService.findOrCreate(tagRequests);

        // member init
        Member member = memberService
                .findOrCreateMember(new GithubProfileResponse("류성현", "gracefulBrown", "46308949", "https://avatars.githubusercontent.com/u/46308949?v=4"));

        // post init
        studylogService.insertPosts(member, Arrays.asList(
                new StudylogRequest("ATDD란 무엇인가", "노션 정리 링크\n개인적으로 친구들에게 한 설명이 참 잘 썼다고 생각한다 호호", mission1.getId(), tagRequests),
                new StudylogRequest("프론트엔드 빌드 툴", "snowpack 사용하기 https://hjuu.tistory.com/6", mission2.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 1", "좋은 내용", mission1.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 2", "좋은 내용", mission2.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 3", "좋은 내용", mission3.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 4", "좋은 내용", mission4.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 5", "좋은 내용", mission1.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 6", "좋은 내용", mission2.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 7", "좋은 내용", mission3.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 8", "좋은 내용", mission4.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 9", "좋은 내용", mission1.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 10", "좋은 내용", mission2.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 11", "좋은 내용", mission3.getId(), tagRequests),
                new StudylogRequest("페이지네이션 데이터 12", "좋은 내용", mission4.getId(), tagRequests)
        ));
    }
}
