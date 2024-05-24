package wooteco.prolog.article.application;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.common.exception.BadRequestCode.ARTICLE_NOT_FOUND_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.ArticleFilterType;
import wooteco.prolog.article.domain.repository.ArticleRepository;
import wooteco.prolog.article.ui.ArticleRequest;
import wooteco.prolog.article.ui.ArticleResponse;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    @Transactional
    public Long create(final ArticleRequest articleRequest, final LoginMember loginMember) {
        final Member member = memberService.findById(loginMember.getId());
        validateMemberIsCrew(member);
        final Article article = articleRequest.toArticle(member);
        return articleRepository.save(article).getId();
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

    @Transactional
    public void updateViewCount(final Long id) {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ARTICLE_NOT_FOUND_EXCEPTION));
        article.updateViewCount();
    }
}
