package wooteco.prolog;

import static wooteco.prolog.DataLoaderApplicationListener.Members.BROWN;
import static wooteco.prolog.DataLoaderApplicationListener.Members.SUNNY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.levellogs.application.LevelLogService;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionMemberService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionMemberRequest;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.PopularStudylogService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.update.UpdateContent;
import wooteco.prolog.update.UpdatedContents;
import wooteco.prolog.update.UpdatedContentsRepository;

/**
 * prolog 서비스를 띄울 때 기본 데이터 저장이 필요하면 init-data profile 로 설정해서 데이터를 초기화할 수 있다.
 */
@Profile({"init-data"})
@AllArgsConstructor
@Configuration
public class DataLoaderApplicationListener implements
	ApplicationListener<ContextRefreshedEvent> {

	private SessionService sessionService;
	private SessionMemberService sessionMemberService;
	private MissionService missionService;
	private TagService tagService;
	private MemberService memberService;
	private StudylogService studylogService;
	private DocumentService studylogDocumentService;
	private AbilityService abilityService;
	private UpdatedContentsRepository updatedContentsRepository;
	private PopularStudylogService popularStudylogService;
	private LevelLogService levelLogService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		studylogDocumentService.deleteAll();

		// session init
		Sessions.init(sessionService);

		// mission init
		Missions.init(missionService);

		// filter init
		TagRequests.init(tagService);

		// member init
		Members.init(memberService);

		sessionMemberService.registerMembers(1L,
			new SessionMemberRequest(Lists.newArrayList(BROWN.value.getId(), Members.SUNNY.value.getId())));

		// post init
		studylogService.insertStudylogs(BROWN.value.getId(), StudylogGenerator.generate(20));
		studylogService.insertStudylogs(Members.JOANNE.value.getId(), StudylogGenerator.generate(20));
		studylogService.insertStudylogs(Members.TYCHE.value.getId(), StudylogGenerator.generate(100));
		studylogService.insertStudylogs(Members.SUNNY.value.getId(), StudylogGenerator.generate(20));

		// ability init
		abilityService.applyDefaultAbilities(Members.JOANNE.value.getId(), "be");
		abilityService.applyDefaultAbilities(Members.SUNNY.value.getId(), "fe");

		updatedContentsRepository
			.save(new UpdatedContents(null, UpdateContent.MEMBER_TAG_UPDATE, 1));
		PageRequest pageRequest = PageRequest.of(0, 10);
		popularStudylogService.updatePopularStudylogs(pageRequest);

		// levelLog init
		levelLogService.insertLevellogs(BROWN.getValue().getId(),
			new LevelLogRequest("더미데이터 넣는 방법", "DataLoderApplication 에서 넣으세요.",
				Arrays.asList(new SelfDiscussionRequest("초기화 하는 방법", "후이에게 부탁하기"))));

        levelLogService.insertLevellogs(SUNNY.getValue().getId(),
            new LevelLogRequest("수달이 서니 이름으로 작성한 레벨로그1", "서니처럼 멋진 개발자가 되는 방법에 대해서 고민해보았습니다.",
                Arrays.asList(new SelfDiscussionRequest("멋진 사람이란", "우테코 수료한사람 ~"))));

        levelLogService.insertLevellogs(SUNNY.getValue().getId(),
            new LevelLogRequest("수달이 서니 이름으로 작성한 레벨로그2", "서니는 언제부터 개발자가 되고 싶었나요?",
                Arrays.asList(new SelfDiscussionRequest("백엔드 개발자란?", "우테코 수료한사람 ~"))));
    }

	private enum Sessions {
		LEVEL1(new SessionRequest("백엔드Java 세션1 - 2021")),
		LEVEL3(new SessionRequest("백엔드Java 세션2 - 2021")),
		LEVEL2(new SessionRequest("프론트엔드JS 세션1 - 2021")),
		LEVEL4(new SessionRequest("프론트엔드JS 세션2 - 2021"));

		private final SessionRequest request;
		private SessionResponse response;

		Sessions(SessionRequest sessionRequest) {
			this.request = sessionRequest;
		}

		public static void init(SessionService sessionService) {
			for (Sessions session : values()) {
				session.response = sessionService.create(session.request);
			}
		}

		public Long getId() {
			return response.getId();
		}
	}

	private enum Missions {
		MISSION1(new MissionRequest("자동차경주", Sessions.LEVEL1.getId())),
		MISSION2(new MissionRequest("로또", Sessions.LEVEL2.getId())),
		MISSION3(new MissionRequest("장바구니", Sessions.LEVEL3.getId())),
		MISSION4(new MissionRequest("지하철", Sessions.LEVEL4.getId()));

		private final MissionRequest request;
		private MissionResponse response;

		Missions(MissionRequest request) {
			this.request = request;
		}

		public static void init(MissionService missionService) {
			for (Missions mission : values()) {
				mission.response = missionService.create(mission.request);
			}
		}

		public Long getId() {
			return response.getId();
		}

	}

	private enum TagRequests {
		TAG_REQUESTS_1234(Arrays.asList(
			new TagRequest("자바"),
			new TagRequest("자바스크립트"),
			new TagRequest("스프링"),
			new TagRequest("리액트")
		)),
		TAG_REQUESTS_EMPTY(Collections.emptyList()),
		TAG_REQUESTS_12(Arrays.asList(
			new TagRequest("자바"),
			new TagRequest("자바스크립트")
		)),
		TAG_REQUESTS_3(Collections.singletonList(
			new TagRequest("스프링")
		));

		private static final Random random = new Random();

		private final List<TagRequest> value;

		TagRequests(List<TagRequest> tagRequests) {
			this.value = tagRequests;
		}

		public static void init(TagService tagService) {
			Arrays.asList(values())
				.forEach(tagRequests -> tagService.findOrCreate(tagRequests.value));
		}

		public static List<TagRequest> random() {
			int i = random.nextInt(values().length);
			return values()[i].value;
		}
	}

	public enum Members {
		BROWN(new GithubProfileResponse(
			"류성현",
			"gracefulBrown",
			"46308949",
			"https://avatars.githubusercontent.com/u/46308949?v=4"
		)),
		JOANNE(new GithubProfileResponse(
			"서민정",
			"seovalue",
			"123456",
			"https://avatars.githubusercontent.com/u/48412963?v=4"
		)),
		TYCHE(new GithubProfileResponse(
			"티케",
			"devhyun637",
			"59258239",
			"https://avatars.githubusercontent.com/u/59258239?v=4"
		)),
		SUNNY(new GithubProfileResponse(
			"박선희",
			"서니",
			"67677561",
			"https://avatars.githubusercontent.com/u/67677561?v=4"
		)),
		HYEON9MAK(new GithubProfileResponse(
			"최현구",
			"hyeon9mak",
			"37354145",
			"https://avatars.githubusercontent.com/u/37354145?v=4"
		));

		private final GithubProfileResponse githubProfileResponse;
		private Member value;

		Members(GithubProfileResponse githubProfileResponse) {
			this.githubProfileResponse = githubProfileResponse;
		}

		public Member getValue() {
			return value;
		}

		public static void init(MemberService memberService) {
			for (Members member : values()) {
				member.value = memberService
					.findOrCreateMember(member.githubProfileResponse);
			}
		}
	}

	private static class StudylogGenerator {

		private static int cnt = 0;

		private StudylogGenerator() {
		}

		public static List<StudylogRequest> generate(int size) {
			List<StudylogRequest> result = new ArrayList<>();

			for (int i = 0; i < size; i++) {
				result.add(create());
			}

			return result;
		}

		private static StudylogRequest create() {
			return new StudylogRequest(
				"페이지네이션 데이터 " + cnt,
				"좋은 내용" + cnt,
				Sessions.values()[cnt++ % Missions.values().length].getId(),
				Missions.values()[cnt++ % Missions.values().length].getId(),
				TagRequests.random(),
				Collections.emptyList()
			);
		}
	}
}
