package wooteco.prolog;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
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
@Configuration
public class DataLoaderApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private MissionService missionService;
    private TagService tagService;
    private GithubLoginService githubLoginService;
    private PostService postService;

    public DataLoaderApplicationListener(MissionService missionService, TagService tagService, GithubLoginService githubLoginService, PostService postService) {
        this.missionService = missionService;
        this.tagService = tagService;
        this.githubLoginService = githubLoginService;
        this.postService = postService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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
        tagService.create(tagRequests);

        // member init
        Member member = githubLoginService.findOrCreateMember(new GithubProfileResponse("류성현", "gracefulBrown", "46308949", "https://avatars.githubusercontent.com/u/46308949?v=4"));

        // post init
        postService.insertPosts(member, Arrays.asList(
                new PostRequest("ATDD란 무엇인가", "노션 정리 링크\n개인적으로 친구들에게 한 설명이 참 잘 썼다고 생각한다 호호", mission1.getId(), tagRequests),
                new PostRequest("프론트엔드 빌드 툴", "snowpack 사용하기 https://hjuu.tistory.com/6", mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 1", "좋은 내용", mission1.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 2", "좋은 내용", mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 3", "좋은 내용", mission3.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 4", "좋은 내용", mission4.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 5", "좋은 내용", mission1.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 6", "좋은 내용", mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 7", "좋은 내용", mission3.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 8", "좋은 내용", mission4.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 9", "좋은 내용", mission1.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 10", "좋은 내용", mission2.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 11", "좋은 내용", mission3.getId(), tagRequests),
                new PostRequest("페이지네이션 데이터 12", "좋은 내용", mission4.getId(), tagRequests)
        ));
    }
}
