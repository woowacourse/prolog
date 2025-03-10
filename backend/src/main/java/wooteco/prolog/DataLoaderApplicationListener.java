package wooteco.prolog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.domain.repository.SelfDiscussionRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.organization.domain.Organization;
import wooteco.prolog.organization.domain.OrganizationGroup;
import wooteco.prolog.organization.domain.OrganizationGroupMember;
import wooteco.prolog.organization.domain.OrganizationGroupSession;
import wooteco.prolog.organization.domain.repository.OrganizationGroupMemberRepository;
import wooteco.prolog.organization.domain.repository.OrganizationGroupRepository;
import wooteco.prolog.organization.domain.repository.OrganizationGroupSessionRepository;
import wooteco.prolog.organization.domain.repository.OrganizationRepository;
import wooteco.prolog.session.domain.Answer;
import wooteco.prolog.session.domain.AnswerFeedback;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.QnaFeedbackContents;
import wooteco.prolog.session.domain.QnaFeedbackRequest;
import wooteco.prolog.session.domain.Question;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.AnswerFeedbackRepository;
import wooteco.prolog.session.domain.repository.AnswerRepository;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.QuestionRepository;
import wooteco.prolog.session.domain.repository.SessionMemberRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.domain.PopularStudylog;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.PopularStudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.TagRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * prolog 서비스를 띄울 때 기본 데이터가 없다면 기본 데이터를 추가한다.
 */
@Profile({"local"})
@AllArgsConstructor
@Configuration
public class DataLoaderApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Random random = new SecureRandom();
    private static final Logger logger = LoggerFactory.getLogger(
        DataLoaderApplicationListener.class);

    private final EntityManagerFactory entityManagerFactory;
    private final SessionRepository sessionRepository;
    private final MissionRepository missionRepository;
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final SessionMemberRepository sessionMemberRepository;
    private final StudylogRepository studylogRepository;
    private final PopularStudylogRepository popularStudylogRepository;
    private final LevelLogRepository levelLogRepository;
    private final SelfDiscussionRepository selfDiscussionRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationGroupRepository organizationGroupRepository;
    private final OrganizationGroupMemberRepository organizationGroupMemberRepository;
    private final OrganizationGroupSessionRepository organizationGroupSessionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerFeedbackRepository answerFeedbackRepository;

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        logger.debug("Start DataLoaderApplicationListener");
        if (shouldSkip()) {
            logger.debug("Skipped data load");
            return;
        }

        cleanUp();
        populate();
    }

    private boolean shouldSkip() {
        return memberRepository.findByUsername(MemberDummy.BROWN.loginName).isPresent();
    }

    private void cleanUp() {
        logger.debug("Start clean up");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            @SuppressWarnings("unchecked") final Set<String> tableNames = (Set<String>) entityManager.createNativeQuery(
                    "SHOW TABLES")
                .getResultStream()
                .map(Object::toString)
                .collect(Collectors.toSet());

            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            tableNames.stream()
                .map(it -> "TRUNCATE TABLE " + it)
                .map(entityManager::createNativeQuery)
                .forEach(it -> {
                    int affectedCount = it.executeUpdate();
                    logger.debug("`{}` affected: {}", it, affectedCount);
                });
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (final Exception e) {
            logger.warn("Fail clean up", e);
            entityManager.getTransaction().rollback();
        }

        logger.debug("Complete clean up");
    }

    private void populate() {
        logger.debug("Start populate");

        final Map<SessionDummy, Session> sessions = populateSession();
        final Map<MissionDummy, Mission> missions = populateMission(sessions);
        final Map<TagsDummy, Tags> tags = populateTags();
        final Map<MemberDummy, Member> members = populateMember();
        final Map<SessionMembersDummy, List<SessionMember>> sessionMembers = populateSessionMembers(sessions, members);
        final Map<StudylogDummy, List<Studylog>> studylogs = populateStudylogs(members, sessions, missions, tags);
        final List<PopularStudylog> popularStudylogs = populatePopularStudyLog(studylogs);
        final Map<LevelLogDummy, LevelLog> levelLogs = populateLevelLog(members);
        final Map<SelfDiscussionDummy, SelfDiscussion> selfDiscussions = populateSelfDiscussion(levelLogs);
        final Map<OrganizationDummy, Organization> organizations = popularOrganization();
        final Map<OrganizationGroupDummy, OrganizationGroup> organizationGroups = popularOrganizationGroup(
            organizations);
        final Map<OrganizationGroupMemberDummy, OrganizationGroupMember> organizationGroupMembers =
            popularOrganizationGroupMember(organizationGroups, members);
        final Map<OrganizationGroupSessionDummy, OrganizationGroupSession> organizationGroupSessions =
            popularOrganizationGroupSession(sessions, organizationGroups);
        final Map<QuestionDummy, Question> questions = popularQuestion(missions);
        final Map<AnswerDummy, Answer> answers = popularAnswer(questions, sessions, missions, members);
        final Map<AnswerFeedbackDummy, AnswerFeedback> answerFeedbacks = popularAnswerFeedback(answers, missions,
            questions, members);

        logger.debug("Complete populate");
    }

    private Map<SessionDummy, Session> populateSession() {
        final Function<SessionDummy, Session> createSession = it -> sessionRepository.save(
            new Session(it.name));

        return Arrays.stream(SessionDummy.values())
            .collect(toMap(Function.identity(), createSession));
    }

    private Map<MissionDummy, Mission> populateMission(final Map<SessionDummy, Session> sessions) {
        final Function<MissionDummy, Mission> createMssion = it -> missionRepository.save(
            new Mission(it.name, sessions.get(it.sessionCategory)));

        return Arrays.stream(MissionDummy.values())
            .collect(toMap(Function.identity(), createMssion));
    }

    private Map<TagsDummy, Tags> populateTags() {
        final Function<TagsDummy, Tags> createTags = it -> {
            final List<Tag> tags = it.value.stream()
                .map(Tag::new)
                .collect(toList());

            return new Tags(tagRepository.saveAll(tags));
        };

        return Arrays.stream(TagsDummy.values())
            .collect(toMap(Function.identity(), createTags));
    }

    private Map<MemberDummy, Member> populateMember() {
        final Function<MemberDummy, Member> createMember = it -> memberRepository.save(new Member(
            it.loginName,
            it.nickname,
            Role.CREW,
            it.githubId,
            it.imageUrl));

        return Arrays.stream(MemberDummy.values())
            .collect(toMap(Function.identity(), createMember));
    }

    private Map<SessionMembersDummy, List<SessionMember>> populateSessionMembers(
        final Map<SessionDummy, Session> sessions, final Map<MemberDummy, Member> members) {
        final Long backendLevel1SessionId = sessions.get(SessionDummy.BACKEND_LEVEL1).getId();

        final Function<SessionMembersDummy, List<SessionMember>> createSessionMembers = it -> {
            final List<SessionMember> sessionMembers = it.value.stream()
                .map(memberDummy -> new SessionMember(backendLevel1SessionId,
                    members.get(memberDummy)))
                .collect(toList());

            return sessionMemberRepository.saveAll(sessionMembers);
        };

        return Arrays.stream(SessionMembersDummy.values())
            .collect(toMap(Function.identity(), createSessionMembers));
    }

    private Map<StudylogDummy, List<Studylog>> populateStudylogs(
        final Map<MemberDummy, Member> members,
        final Map<SessionDummy, Session> sessions,
        final Map<MissionDummy, Mission> missions,
        final Map<TagsDummy, Tags> tags
    ) {
        final Function<StudylogDummy, List<Studylog>> insertStudyLogs = it -> {
            final Member member = members.get(it.memberDummy);

            final List<Studylog> studylogs = new ArrayList<>(it.postCount);
            for (int i = 0; i < it.postCount; i++) {
                studylogs.add(new Studylog(
                    member,
                    "페이지네이션 데이터 " + i,
                    "좋은 내용" + i,
                    pickRandom(sessions.values()),
                    pickRandom(missions.values()),
                    pickRandom(tags.values()).getList()
                ));
            }

            return studylogRepository.saveAll(studylogs);
        };

        return Arrays.stream(StudylogDummy.values())
            .collect(toMap(Function.identity(), insertStudyLogs));
    }

    private <T> T pickRandom(final Collection<T> values) {
        final List<T> list = new ArrayList<>(values);
        return list.get(random.nextInt(list.size()));
    }

    private List<PopularStudylog> populatePopularStudyLog(
        final Map<StudylogDummy, List<Studylog>> studylogs) {
        final List<PopularStudylog> randomPopularStudyLogs = studylogs.values()
            .stream()
            .flatMap(Collection::stream)
            .map(Studylog::getId)
            .map(PopularStudylog::new)
            .sorted((ignored1, ignored2) -> random.nextInt()) // Note: Random Sort
            .limit(10)
            .collect(toList());

        return popularStudylogRepository.saveAll(randomPopularStudyLogs);
    }

    private Map<LevelLogDummy, LevelLog> populateLevelLog(final Map<MemberDummy, Member> members) {
        final Function<LevelLogDummy, LevelLog> createLevelLog = it -> levelLogRepository.save(
            new LevelLog(it.title, it.contents, members.get(it.memberDummy)));

        return Arrays.stream(LevelLogDummy.values())
            .collect(toMap(Function.identity(), createLevelLog));
    }

    private Map<SelfDiscussionDummy, SelfDiscussion> populateSelfDiscussion(
        final Map<LevelLogDummy, LevelLog> levelLogs) {
        final Function<SelfDiscussionDummy, SelfDiscussion> createSelfDiscussion = it -> selfDiscussionRepository.save(
            new SelfDiscussion(levelLogs.get(it.levelLogDummy), it.question, it.answer));

        return Arrays.stream(SelfDiscussionDummy.values())
            .collect(toMap(Function.identity(), createSelfDiscussion));
    }

    private Map<OrganizationDummy, Organization> popularOrganization() {
        final Function<OrganizationDummy, Organization> createOrganization = it -> organizationRepository.save(
            new Organization(it.name));

        return Arrays.stream(OrganizationDummy.values())
            .collect(toMap(Function.identity(), createOrganization));
    }

    private Map<OrganizationGroupDummy, OrganizationGroup> popularOrganizationGroup(
        final Map<OrganizationDummy, Organization> organizations
    ) {
        final var organization = organizations.get(OrganizationDummy.TECH_COURSE);
        final Function<OrganizationGroupDummy, OrganizationGroup> createOrganizationGroup = it -> organizationGroupRepository.save(
            new OrganizationGroup(organization.getId(), it.name));

        return Arrays.stream(OrganizationGroupDummy.values())
            .collect(toMap(Function.identity(), createOrganizationGroup));
    }

    private Map<OrganizationGroupMemberDummy, OrganizationGroupMember> popularOrganizationGroupMember(
        final Map<OrganizationGroupDummy, OrganizationGroup> organizationGroups,
        final Map<MemberDummy, Member> members
    ) {
        final Function<OrganizationGroupMemberDummy, OrganizationGroupMember> createOrganizationGroupMember = it -> {
            final var member = members.get(it.memberDummy);
            final var organizationGroup = organizationGroups.get(it.organizationGroupDummy);
            final var organizationGroupMember = new OrganizationGroupMember(
                organizationGroup.getId(),
                member.getUsername(),
                member.getNickname()
            );
            organizationGroupMember.updateMemberId(member.getId());
            return organizationGroupMemberRepository.save(organizationGroupMember);
        };
        return Arrays.stream(OrganizationGroupMemberDummy.values())
            .collect(toMap(Function.identity(), createOrganizationGroupMember));
    }

    private Map<OrganizationGroupSessionDummy, OrganizationGroupSession> popularOrganizationGroupSession(
        final Map<SessionDummy, Session> sessions,
        final Map<OrganizationGroupDummy, OrganizationGroup> organizationGroups
    ) {
        final Function<OrganizationGroupSessionDummy, OrganizationGroupSession> createOrganizationGroupSession = it -> {
            final var session = sessions.get(it.sessionDummy);
            final var organizationGroup = organizationGroups.get(it.organizationGroupDummy);
            return organizationGroupSessionRepository.save(new OrganizationGroupSession(
                organizationGroup.getId(),
                session
            ));
        };

        return Arrays.stream(OrganizationGroupSessionDummy.values())
            .collect(toMap(Function.identity(), createOrganizationGroupSession));
    }

    private Map<QuestionDummy, Question> popularQuestion(final Map<MissionDummy, Mission> missions) {
        final Function<QuestionDummy, Question> createQuestion = it -> questionRepository.save(
            new Question(it.question, missions.get(it.missionDummy)));

        return Arrays.stream(QuestionDummy.values())
            .collect(toMap(Function.identity(), createQuestion));
    }

    private Map<AnswerDummy, Answer> popularAnswer(
        final Map<QuestionDummy, Question> questions,
        final Map<SessionDummy, Session> sessions,
        final Map<MissionDummy, Mission> missions,
        final Map<MemberDummy, Member> members
    ) {
        final Function<AnswerDummy, Answer> createAnswer = it -> {
            final var member = members.get(it.memberDummy);
            final var studylog = studylogRepository.save(new Studylog(
                member,
                "학습로그 답변 데이터",
                "좋은 내용",
                sessions.get(it.sessionDummy),
                missions.get(it.missionDummy),
                List.of()
            ));
            return answerRepository.save(new Answer(
                studylog,
                questions.get(it.questionDummy),
                member.getId(),
                "다 어려워요."
            ));
        };

        return Arrays.stream(AnswerDummy.values())
            .collect(toMap(Function.identity(), createAnswer));
    }

    private Map<AnswerFeedbackDummy, AnswerFeedback> popularAnswerFeedback(
        final Map<AnswerDummy, Answer> answers,
        final Map<MissionDummy, Mission> missions,
        final Map<QuestionDummy, Question> questions,
        final Map<MemberDummy, Member> members
    ) {
        final Function<AnswerFeedbackDummy, AnswerFeedback> createAnswerFeedback = it -> {
            final var answer = answers.get(it.answerDummy);
            final var member = members.get(it.memberDummy);
            final var mission = missions.get(it.missionDummy);
            final var question = questions.get(it.questionDummy);

            return answerFeedbackRepository.save(new AnswerFeedback(
                answer.getQuestion(),
                member.getId(),
                new QnaFeedbackRequest(mission.getGoal(), question.getContent(), answer.getContent()),
                it.contents
            ));
        };

        return Arrays.stream(AnswerFeedbackDummy.values())
            .collect(toMap(Function.identity(), createAnswerFeedback));
    }

    private enum SessionDummy {
        BACKEND_LEVEL1("백엔드Java 세션1 - 2021"),
        BACKEND_LEVEL2("백엔드Java 세션2 - 2021"),
        FRONTEND_LEVEL3("프론트엔드JS 세션1 - 2021"),
        FRONTEND_LEVEL4("프론트엔드JS 세션2 - 2021");

        final String name;

        SessionDummy(final String name) {
            this.name = name;
        }
    }

    private enum MissionDummy {
        MISSION1("자동차경주", SessionDummy.BACKEND_LEVEL1, "잘 구현하자"),
        MISSION2("로또", SessionDummy.BACKEND_LEVEL2),
        MISSION3("장바구니", SessionDummy.FRONTEND_LEVEL3),
        MISSION4("지하철", SessionDummy.FRONTEND_LEVEL4);

        final String name;
        final SessionDummy sessionCategory;
        final String goal;

        MissionDummy(final String name, final SessionDummy sessionCategory) {
            this(name, sessionCategory, "");
        }

        MissionDummy(final String name, final SessionDummy sessionCategory, final String goal) {
            this.name = name;
            this.sessionCategory = sessionCategory;
            this.goal = goal;
        }
    }

    private enum TagsDummy {
        EMPTY,
        LANGUAGE("자바", "자바스크립트"),
        SPRING("스프링"),
        PROJECT("자바", "자바스크립트", "스프링", "리액트");

        final List<String> value;

        TagsDummy(final String... value) {
            this(Arrays.asList(value));
        }

        TagsDummy(final List<String> value) {
            this.value = value;
        }
    }

    private enum MemberDummy {
        BROWN("류성현", "gracefulBrown", 46308949,
            "https://avatars.githubusercontent.com/u/46308949?v=4"),
        JOANNE("서민정", "seovalue", 123456, "https://avatars.githubusercontent.com/u/48412963?v=4"),
        TYCHE("티케", "devhyun637", 59258239, "https://avatars.githubusercontent.com/u/59258239?v=4"),
        SUNNY("박선희", "서니", 67677561, "https://avatars.githubusercontent.com/u/67677561?v=4"),
        HYEON9MAK("최현구", "hyeon9mak", 37354145,
            "https://avatars.githubusercontent.com/u/37354145?v=4"),
        JAEYEONLING("김재연", "jaeyeonling", 40811999,
            "https://avatars.githubusercontent.com/u/40811999?v=4");

        final String nickname;
        final String loginName;
        final long githubId;
        final String imageUrl;

        MemberDummy(final String nickname, final String loginName, final long githubId,
                    final String imageUrl) {
            this.nickname = nickname;
            this.loginName = loginName;
            this.githubId = githubId;
            this.imageUrl = imageUrl;
        }
    }

    private enum SessionMembersDummy {
        BACKEND_LEVEL1_MEMBER(MemberDummy.BROWN, MemberDummy.SUNNY, MemberDummy.JAEYEONLING);

        final List<MemberDummy> value;

        SessionMembersDummy(final MemberDummy... value) {
            this(Arrays.asList(value));
        }

        SessionMembersDummy(final List<MemberDummy> value) {
            this.value = value;
        }
    }

    private enum StudylogDummy {
        POST_BY_BROWN(MemberDummy.BROWN, 20),
        POST_BY_JOANNE(MemberDummy.JOANNE, 20),
        POST_BY_TYCHE(MemberDummy.TYCHE, 100),
        POST_BY_SUNNY(MemberDummy.SUNNY, 20),
        POST_BY_JAEYEONLING(MemberDummy.JAEYEONLING, 1);

        final MemberDummy memberDummy;
        final int postCount;

        StudylogDummy(final MemberDummy memberDummy, final int postCount) {
            this.memberDummy = memberDummy;
            this.postCount = postCount;
        }
    }

    private enum LevelLogDummy {
        HOW_TO_SET_DUMMY(MemberDummy.BROWN, "더미데이터 넣는 방법", "DataLoderApplication 에서 넣으세요."),
        SECRETLY_WRITTEN_LEVEL_LOG_1(MemberDummy.SUNNY, "수달이 서니 이름으로 작성한 레벨로그1",
            "서니처럼 멋진 개발자가 되는 방법에 대해서 고민해보았습니다."),
        SECRETLY_WRITTEN_LEVEL_LOG_2(MemberDummy.SUNNY, "수달이 서니 이름으로 작성한 레벨로그2",
            "서니는 언제부터 개발자가 되고 싶었나요?");

        final MemberDummy memberDummy;
        final String title;
        final String contents;

        LevelLogDummy(final MemberDummy memberDummy, final String title, final String contents) {
            this.memberDummy = memberDummy;
            this.title = title;
            this.contents = contents;
        }
    }

    private enum SelfDiscussionDummy {
        HOW_TO_RESET("초기화 하는 방법", "후이에게 부탁하기", LevelLogDummy.HOW_TO_SET_DUMMY),
        WHAT_IS_COOL_PERSON("멋진 사람이란", "우테코 수료한사람 ~", LevelLogDummy.SECRETLY_WRITTEN_LEVEL_LOG_1),
        WHAT_IS_BACKEND_DEVELOPER("백엔드 개발자란?", "우테코 수료한사람 ~",
            LevelLogDummy.SECRETLY_WRITTEN_LEVEL_LOG_2);

        final String question;
        final String answer;
        final LevelLogDummy levelLogDummy;

        SelfDiscussionDummy(final String question, final String answer,
                            final LevelLogDummy levelLogDummy) {
            this.question = question;
            this.answer = answer;
            this.levelLogDummy = levelLogDummy;
        }
    }

    private enum OrganizationDummy {
        TECH_COURSE("우아한테크코스");

        final String name;

        OrganizationDummy(final String name) {
            this.name = name;
        }
    }

    private enum OrganizationGroupDummy {
        BACKEND("7기 백엔드");

        final String name;

        OrganizationGroupDummy(final String name) {
            this.name = name;
        }
    }

    private enum OrganizationGroupMemberDummy {
        BACKEND_MEMBER(MemberDummy.BROWN, OrganizationGroupDummy.BACKEND),
        BACKEND_MEMBER2(MemberDummy.JAEYEONLING, OrganizationGroupDummy.BACKEND);

        final MemberDummy memberDummy;
        final OrganizationGroupDummy organizationGroupDummy;

        OrganizationGroupMemberDummy(
            final MemberDummy memberDummy,
            final OrganizationGroupDummy organizationGroupDummy
        ) {
            this.memberDummy = memberDummy;
            this.organizationGroupDummy = organizationGroupDummy;
        }
    }

    private enum OrganizationGroupSessionDummy {
        BACKEND_SESSION(SessionDummy.BACKEND_LEVEL1, OrganizationGroupDummy.BACKEND);

        final SessionDummy sessionDummy;
        final OrganizationGroupDummy organizationGroupDummy;

        OrganizationGroupSessionDummy(
            final SessionDummy sessionDummy,
            final OrganizationGroupDummy organizationGroupDummy
        ) {
            this.sessionDummy = sessionDummy;
            this.organizationGroupDummy = organizationGroupDummy;
        }
    }

    private enum QuestionDummy {
        QUESTION1("자동차 경주 게임을 구현하면서 어려웠던 점은 무엇이었나요?", MissionDummy.MISSION1);

        final String question;
        final MissionDummy missionDummy;

        QuestionDummy(final String question, final MissionDummy missionDummy) {
            this.question = question;
            this.missionDummy = missionDummy;
        }
    }

    private enum AnswerDummy {
        ANSWER1(QuestionDummy.QUESTION1, SessionDummy.BACKEND_LEVEL1, MissionDummy.MISSION1, MemberDummy.JAEYEONLING);

        final QuestionDummy questionDummy;
        final SessionDummy sessionDummy;
        final MissionDummy missionDummy;
        final MemberDummy memberDummy;

        AnswerDummy(
            final QuestionDummy questionDummy,
            final SessionDummy sessionDummy,
            final MissionDummy missionDummy,
            final MemberDummy memberDummy
        ) {
            this.questionDummy = questionDummy;
            this.sessionDummy = sessionDummy;
            this.missionDummy = missionDummy;
            this.memberDummy = memberDummy;
        }
    }

    private enum AnswerFeedbackDummy {
        FEEDBACK1(QuestionDummy.QUESTION1, MemberDummy.JAEYEONLING, MissionDummy.MISSION1, AnswerDummy.ANSWER1,
            new QnaFeedbackContents("좋아요", "더 노력해요", "더 공부해요", 5));

        final QuestionDummy questionDummy;
        final MemberDummy memberDummy;
        final MissionDummy missionDummy;
        final AnswerDummy answerDummy;
        final QnaFeedbackContents contents;

        AnswerFeedbackDummy(
            final QuestionDummy questionDummy,
            final MemberDummy memberDummy,
            final MissionDummy missionDummy,
            final AnswerDummy answerDummy,
            final QnaFeedbackContents contents
        ) {
            this.questionDummy = questionDummy;
            this.memberDummy = memberDummy;
            this.missionDummy = missionDummy;
            this.answerDummy = answerDummy;
            this.contents = contents;
        }
    }
}
