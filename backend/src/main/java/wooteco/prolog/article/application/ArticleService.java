package wooteco.prolog.article.application;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.article.application.dto.ArticleRequest;
import wooteco.prolog.article.application.dto.ArticleResponse;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.ArticleFilterType;
import wooteco.prolog.article.domain.RssFeed;
import wooteco.prolog.article.domain.RssFeeds;
import wooteco.prolog.article.domain.repository.ArticleRepository;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberUpdatedEvent;
import wooteco.prolog.member.domain.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.common.exception.BadRequestCode.ARTICLE_NOT_FOUND_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final SlackService slackService;
    private final RssClient rssClient;

    @Transactional
    public Long create(final ArticleRequest articleRequest, final LoginMember loginMember) {
        final Member member = memberService.findById(loginMember.getId());
        validateMemberIsCrew(member);
        Article article = articleRepository.save(articleRequest.toArticle(member));

        slackService.sendSlackMessage(article);
        return article.getId();
    }

    private void validateMemberIsCrew(final Member member) {
        if (member.hasLowerImportanceRoleThan(Role.CREW)) {
            throw new BadRequestException(MEMBER_NOT_ALLOWED);
        }
    }

    @Transactional
    public void update(final Long id, final ArticleRequest articleRequest,
                       final LoginMember loginMember) {
        final Member member = memberService.findById(loginMember.getId());
        validateMemberIsCrew(member);
        final Article article = findArticleByIdThrowIfNotExist(id);
        article.validateOwner(member);
        article.update(articleRequest.getTitle(), articleRequest.getUrl());
    }

    private Article findArticleByIdThrowIfNotExist(final Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public void delete(final Long id, final LoginMember loginMember) {
        final Member member = memberService.findById(loginMember.getId());
        validateMemberIsCrew(member);
        final Article article = findArticleByIdThrowIfNotExist(id);
        article.validateOwner(member);
        articleRepository.delete(article);
    }

    @Transactional
    public void bookmarkArticle(final Long id, final LoginMember loginMember,
                                final Boolean isBookmark) {
        final Article article = articleRepository.findFetchBookmarkById(id)
            .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));
        final Member member = memberService.findById(loginMember.getId());
        article.setBookmark(member, isBookmark);
    }

    @Transactional
    public void likeArticle(final Long id, final LoginMember loginMember, final Boolean isLike) {
        final Article article = articleRepository.findFetchLikeById(id)
            .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));
        final Member member = memberService.findById(loginMember.getId());
        article.setLike(member, isLike);
    }

    public List<ArticleResponse> getFilteredArticles(final LoginMember member,
                                                     final ArticleFilterType course,
                                                     final boolean onlyBookmarked) {
        return articleRepository.findAllByOrderByCreatedAtDesc().stream()
            .map(article -> ArticleResponse.of(article, member.getId()))
            .collect(toList());
    }

    public List<ArticleResponse> getArticlesByPublishedAt(final LoginMember member) {
        return articleRepository.findAllOrderByPublishedAtOrCreatedAt().stream()
            .map(article -> ArticleResponse.of(article, member.getId()))
            .collect(toList());
    }

    @Transactional
    public void updateViewCount(final Long id) {
        final Article article = articleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));
        article.updateViewCount();
    }

    @EventListener
    public void handleMemberUpdatedEvent(MemberUpdatedEvent event) {
        Member member = event.getMember();
        fetchArticlesOf(member);
    }

    @Transactional
    public List<ArticleResponse> fetchArticlesOf(String username) {
        if (username == null || username.isEmpty()) {
            return fetchArticleWithRssFeeds();
        }

        Member member = memberService.findByUsername(username);
        return fetchArticleWithRssFeedOf(member);
    }

    private List<ArticleResponse> fetchArticleWithRssFeeds() {
        List<Member> members = memberService.findMembersWhoHasRssFeedLink();

        return members.stream()
            .map(this::fetchArticleWithRssFeedOf)
            .flatMap(List::stream)
            .collect(toList());
    }

    private List<ArticleResponse> fetchArticleWithRssFeedOf(Member member) {
        List<Article> newArticles = fetchArticlesOf(member);
        if (newArticles.isEmpty()) {
            return new ArrayList<>();
        }

        // 가장 최신의 글만 슬랙 메시지로 알림
        newArticles.stream()
            .max(Comparator.comparing(Article::getPublishedAt))
            .ifPresent(slackService::sendSlackMessage);

        // 저장된 모든 아티클 출력
        return newArticles.stream()
            .map(article -> ArticleResponse.of(article, member.getId()))
            .collect(toList());
    }

    private List<Article> fetchArticlesOf(Member member) {
        try {
            RssFeeds rssFeeds = rssClient.fromRssFeedBy(member.getRssFeedUrl());
            RssFeed latestRssFeed = rssFeeds.findLatestRssFeed();

            // 만약에 가장 최신의 피드가 없으면? 끝
            if (rssFeeds.isEmpty()) {
                return new ArrayList<>();
            }

            // 기존에 하나도 저장된 아티클이 없으면? 가장 최신의 피드를 저장
            List<Article> existedArticles = articleRepository.findAllByMemberId(member.getId());
            if (existedArticles.isEmpty()) {
                return Lists.newArrayList(articleRepository.save(latestRssFeed.toArticle(member)));
            }

            // 가장 최근 아티클의 발행시간 조회
            LocalDateTime latestArticlePublishedAt = existedArticles.stream()
                .max(Comparator.comparing(Article::getPublishedAt)).get().getPublishedAt();

            // 최근 아티클 발행 시간 이후로 작성된 피드 추출
            List<RssFeed> articlesAfter = rssFeeds.findArticlesAfter(latestArticlePublishedAt);

            // 최근 아티클 발행 시간 이후로 작성된 피드를 모두 저장
            return articleRepository.saveAll(articlesAfter.stream()
                .map(it -> it.toArticle(member))
                .collect(toList()));
        } catch (Exception e) {
            logger.error("Failed to fetch RSS feed for member: " + member.getId(), e);
            throw new RssFeedException("Failed to fetch RSS feed for member: " + member.getId(), e);
        }
    }
}
