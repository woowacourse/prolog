package wooteco.prolog.article.application;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.common.exception.BadRequestCode.ARTICLE_NOT_FOUND_EXCEPTION;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.ArticleFilterType;
import wooteco.prolog.article.domain.repository.ArticleRepository;
import wooteco.prolog.article.ui.ArticleRequest;
import wooteco.prolog.article.ui.ArticleResponse;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    @Transactional
    public Long create(final ArticleRequest articleRequest, final LoginMember loginMember) {
        final Member member = memberService.findById(loginMember.getId());
        if (member.isAnonymous()) {
            throw new BadRequestException(BadRequestCode.UNVALIDATED_MEMBER_EXCEPTION);
        }
        final Article article = articleRequest.toArticle(member);
        return articleRepository.save(article).getId();
    }

    @Transactional
    public void update(final Long id, final ArticleRequest articleRequest,
                       final LoginMember loginMember) {
        final Article article = articleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));

        final Member member = memberService.findById(loginMember.getId());
        article.validateOwner(member);

        article.update(articleRequest.getTitle(), articleRequest.getUrl());
    }

    @Transactional
    public void delete(final Long id, final LoginMember loginMember) {
        final Article article = articleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));

        final Member member = memberService.findById(loginMember.getId());
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
        if (member.isMember() && onlyBookmarked) {
            return articleRepository.findArticlesByCourseAndMember(course.getGroupName(),
                    member.getId()).stream()
                .map(article -> ArticleResponse.of(article, member.getId()))
                .collect(toList());
        }

        return articleRepository.findArticlesByCourse(course.getGroupName()).stream()
            .map(article -> ArticleResponse.of(article, member.getId()))
            .collect(toList());
    }

    @Transactional
    public void updateViewCount(final Long id) {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));
        article.updateViewCount();
    }
}
