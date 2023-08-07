package wooteco.prolog.article.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.article.domain.Article;
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
        final Article article = articleRequest.toArticle(member);
        return articleRepository.save(article).getId();
    }

    public List<ArticleResponse> getAll() {
        return articleRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(ArticleResponse::from)
            .collect(toList());
    }

    @Transactional
    public void update(final Long id, final ArticleRequest articleRequest,
                       final LoginMember loginMember) {
        final Article article = articleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(BadRequestCode.ARTICLE_NOT_FOUND_EXCEPTION));

        final Member member = memberService.findById(loginMember.getId());
        article.validateOwner(member);

        article.update(articleRequest.getTitle(), articleRequest.getUrl());
    }

    @Transactional
    public void delete(final Long id, final LoginMember loginMember) {
        final Article article = articleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(BadRequestCode.ARTICLE_NOT_FOUND_EXCEPTION));

        final Member member = memberService.findById(loginMember.getId());
        article.validateOwner(member);

        articleRepository.delete(article);
    }
}
