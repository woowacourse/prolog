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
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.application.TagService;
import wooteco.prolog.tag.dto.TagRequest;

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
    private PostService postService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // level init
        LevelResponse level1 = levelService.create(new LevelRequest("레벨1"));
        LevelResponse level2 = levelService.create(new LevelRequest("레벨2"));

        // mission init
        MissionResponse mission1 = missionService.create(new MissionRequest("3기-프론트엔드-레벨1-자동차경주"));
        MissionResponse mission2 = missionService.create(new MissionRequest("3기-백엔드-레벨1-로또"));
        MissionResponse mission3 = missionService.create(new MissionRequest("3기-프론트엔드-레벨2-장바구니"));
        MissionResponse mission4 = missionService.create(new MissionRequest("3기-백엔드-레벨2-지하철"));

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
        postService.insertPosts(member, Arrays.asList(
                new PostRequest("ATDD란 무엇인가", "노션 정리 링크\n개인적으로 친구들에게 한 설명이 참 잘 썼다고 생각한다 호호", level1.getId(), mission1.getId(), tagRequests),
                new PostRequest("프론트엔드 빌드 툴", "snowpack 사용하기 https://hjuu.tistory.com/6", level1.getId(), mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 1", "좋은 내용", level1.getId(), mission1.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 2", "좋은 내용", level1.getId(), mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 3", "좋은 내용", level2.getId(), mission3.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 4", "좋은 내용", level2.getId(), mission4.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 5", "좋은 내용", level1.getId(), mission1.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 6", "좋은 내용", level1.getId(), mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 7", "좋은 내용", level2.getId(), mission3.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 8", "좋은 내용", level2.getId(), mission4.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 9", "좋은 내용", level1.getId(), mission1.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 10", "좋은 내용", level1.getId(), mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 11", "좋은 내용", level2.getId(), mission3.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 12", "좋은 내용", level2.getId(), mission4.getId(), tagRequests)
        ));
    }
}
