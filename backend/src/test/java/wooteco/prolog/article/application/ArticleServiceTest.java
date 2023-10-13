package wooteco.prolog.article.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.ArticleBookmarks;
import wooteco.prolog.article.domain.ArticleFilterType;
import wooteco.prolog.article.domain.ArticleLikes;
import wooteco.prolog.article.domain.ImageUrl;
import wooteco.prolog.article.domain.Title;
import wooteco.prolog.article.domain.Url;
import wooteco.prolog.article.domain.repository.ArticleRepository;
import wooteco.prolog.article.ui.ArticleRequest;
import wooteco.prolog.article.ui.ArticleResponse;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.login.ui.LoginMember.Authority.ANONYMOUS;
import static wooteco.prolog.login.ui.LoginMember.Authority.MEMBER;
import static wooteco.prolog.member.domain.Role.CREW;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private ArticleService articleService;

    @DisplayName("아티클을 생성한다.")
    @Test
    void create_success() {
        //given
        final ArticleRequest judyRequest = new ArticleRequest("title", "url", "imageUrl");
        final Member member = new Member(1L, "username", "nickname", CREW, 1L, "url");
        when(memberService.findById(any())).thenReturn(member);

        final Article article = new Article(member, new Title("title"), new Url("url"),
                new ImageUrl("imageUrl"));
        when(articleRepository.save(any())).thenReturn(article);
        final LoginMember judyLogin = new LoginMember(1L, MEMBER);

        //when
        articleService.create(judyRequest, judyLogin);

        //then
        verify(articleRepository).save(any());
    }

    @DisplayName("게스트일시 아티클을 생성하려고 하면 예외가 발생한다.")
    @Test
    void create_fail_guest() {
        //given
        final ArticleRequest judyRequest = new ArticleRequest("title", "url", "imageUrl");
        final Member member = new Member(1L, "username", "nickname", Role.GUEST, 1L, "url");
        when(memberService.findById(any())).thenReturn(member);
        final LoginMember judyLogin = new LoginMember(1L, MEMBER);

        //when
        //then
        assertThatThrownBy(() -> articleService.create(judyRequest, judyLogin))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("아티클을 수정한다.")
    @Test
    void update_success() {
        //given
        final Member judy = new Member(1L, "username", "nickname", CREW, 1L, "url");
        final Article judyArticle = new Article(judy, new Title("judyTitle"), new Url("judyUrl"),
                new ImageUrl("imageUrl"));
        when(articleRepository.findById(any())).thenReturn(Optional.of(judyArticle));
        when(memberService.findById(any())).thenReturn(judy);

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);
        final ArticleRequest judyChangedRequest = new ArticleRequest("title", "changedUrl",
                "imageUrl");

        //when
        articleService.update(1L, judyChangedRequest, judyLogin);

        //then
        assertThat(judyArticle.getUrl().getUrl()).isEqualTo("changedUrl");
    }

    @DisplayName("수정시 id에 해당하는 아티클이 존재하지 않을 때 예외를 발생시킨다.")
    @Test
    void update_fail_ArticleNotFoundException() {
        //given
        final Member judy = new Member(1L, "judith", "judy", Role.CREW, 1L, "judyUrl");

        when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(memberService.findById(any())).thenReturn(judy);

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);
        final ArticleRequest judyChangedRequest = new ArticleRequest("title", "changedUrl",
                "imageUrl");

        //when, then
        assertThatThrownBy(() -> articleService.update(1L, judyChangedRequest, judyLogin))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("수정시 아티클 작성자가 아니라면 예외를 발생시킨다.")
    @Test
    void update_fail_InvalidArticleAuthorException() {
        //given
        final Member judy = new Member(1L, "judith", "judy", CREW, 1L, "judyUrl");
        final Member brown = new Member(2L, "brown", "brownie", CREW, 2L, "brownUrl");
        final Article brownArticle = new Article(brown, new Title("brownTitle"),
                new Url("brownUrl"), new ImageUrl("imageUrl"));
        when(articleRepository.findById(any())).thenReturn(Optional.of(brownArticle));

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);
        when(memberService.findById(any())).thenReturn(judy);
        final ArticleRequest judyChangedRequest = new ArticleRequest("title", "changedUrl",
                "imageUrl");

        //when, then
        assertThatThrownBy(() -> articleService.update(1L, judyChangedRequest, judyLogin))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("아티클을 삭제한다.")
    @Test
    void delete_success() {
        //given
        final Member judy = new Member(1L, "judith", "judy", CREW, 1L, "judyUrl");
        final Article judyArticle = new Article(judy, new Title("judyTitle"), new Url("judyUrl"),
                new ImageUrl("imageUrl"));
        when(articleRepository.findById(any())).thenReturn(Optional.of(judyArticle));
        when(memberService.findById(any())).thenReturn(judy);
        final LoginMember judyLogin = new LoginMember(1L, MEMBER);

        //when
        articleService.delete(1L, judyLogin);

        //then
        verify(articleRepository, atLeastOnce()).delete(judyArticle);
    }

    @DisplayName("삭제시 id에 해당하는 아티클이 존재하지 않을 때 예외를 발생시킨다.")
    @Test
    void delete_fail_ArticleNotFoundException() {
        //given
        final Member judy = new Member(1L, "judith", "judy", Role.CREW, 1L, "judyUrl");

        when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(memberService.findById(any())).thenReturn(judy);

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);

        //when, then
        assertThatThrownBy(() -> articleService.delete(1L, judyLogin))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("삭제시 아티클 작성자가 아니라면 예외를 발생시킨다.")
    @Test
    void delete_fail_InvalidArticleAuthorException() {
        //given
        final Member judy = new Member(1L, "judith", "judy", CREW, 1L, "judyUrl");
        final Member brown = new Member(2L, "brown", "brownie", CREW, 2L, "brownUrl");
        final Article brownArticle = new Article(brown, new Title("brownTitle"),
                new Url("brownUrl"), new ImageUrl("imageUrl"));
        when(articleRepository.findById(any())).thenReturn(Optional.of(brownArticle));

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);
        when(memberService.findById(any())).thenReturn(judy);

        //when, then
        assertThatThrownBy(() -> articleService.delete(1L, judyLogin))
                .isInstanceOf(BadRequestException.class);
    }

    @Nested
    @DisplayName("아티클의 북마크 상태를 바꿀 수 있다.")
    class bookmarkArticle {

        @DisplayName("아티클의 북마크를 추가한다.")
        @Test
        void add() {
            //given
            final Member member = new Member(1L, "userName", "nickName",
                    CREW, 1L, "imageUrl");
            final Article article = new Article(member, new Title("brownTitle"),
                    new Url("brownUrl"), new ImageUrl("imageUrl"));
            final Long articleId = 3L;
            final LoginMember loginMember = new LoginMember(member.getId(), MEMBER);

            when(articleRepository.findFetchBookmarkById(articleId)).thenReturn(
                    Optional.of(article));
            when(memberService.findById(member.getId())).thenReturn(member);

            //when
            articleService.bookmarkArticle(articleId, loginMember, true);

            //then
            final ArticleBookmarks articleBookmarks = article.getArticleBookmarks();
            assertThat(articleBookmarks.containBookmark(member.getId()))
                    .isTrue();
        }

        @DisplayName("아티클의 북마크를 삭제한다.")
        @Test
        void remove() {
            //given
            final Member member = new Member(1L, "userName", "nickName",
                    CREW, 1L, "imageUrl");
            final Article article = new Article(member, new Title("brownTitle"),
                    new Url("brownUrl"), new ImageUrl("imageUrl"));
            final Long articleId = 3L;
            final LoginMember loginMember = new LoginMember(member.getId(), MEMBER);

            when(articleRepository.findFetchBookmarkById(articleId)).thenReturn(
                    Optional.of(article));
            when(memberService.findById(member.getId())).thenReturn(member);

            //when
            articleService.bookmarkArticle(articleId, loginMember, true);

            //then
            final ArticleBookmarks articleBookmarks = article.getArticleBookmarks();
            assertThat(articleBookmarks.containBookmark(member.getId()))
                    .isTrue();
        }
    }

    @Nested
    @DisplayName("아티클의 좋아요 상태를 바꿀 수 있다.")
    class likeArticle {

        @DisplayName("아티클에 좋아요를 추가한다.")
        @Test
        void add() {
            //given
            final Member member = new Member(1L, "userName", "nickName",
                    CREW, 1L, "imageUrl");
            final Article article = new Article(member, new Title("brownTitle"),
                    new Url("brownUrl"), new ImageUrl("imageUrl"));
            final Long articleId = 3L;
            final LoginMember loginMember = new LoginMember(member.getId(), MEMBER);

            when(articleRepository.findFetchLikeById(articleId)).thenReturn(Optional.of(article));
            when(memberService.findById(member.getId())).thenReturn(member);

            //when
            articleService.likeArticle(articleId, loginMember, true);

            //then
            final ArticleLikes articleBookmarks = article.getArticleLikes();
            assertThat(articleBookmarks.isAlreadyLike(member.getId()))
                    .isTrue();
        }

        @DisplayName("아티클의 좋아요를 삭제한다.")
        @Test
        void remove() {
            //given
            final Member member = new Member(1L, "userName", "nickName",
                    CREW, 1L, "imageUrl");
            final Article article = new Article(member, new Title("brownTitle"),
                    new Url("brownUrl"), new ImageUrl("imageUrl"));
            final Long articleId = 3L;
            final LoginMember loginMember = new LoginMember(member.getId(), MEMBER);

            when(articleRepository.findFetchLikeById(articleId)).thenReturn(Optional.of(article));
            when(memberService.findById(member.getId())).thenReturn(member);

            //when
            articleService.likeArticle(articleId, loginMember, false);

            //then
            final ArticleLikes articleBookmarks = article.getArticleLikes();
            assertThat(articleBookmarks.isAlreadyLike(member.getId()))
                    .isFalse();
        }
    }

    @DisplayName("비로그인 사용자가 백엔드 아티클을 필터링 한다.")
    @Test
    void filter() {
        //given
        final Member member = new Member(1L, "username", "nickname", CREW, 1L, "url");
        final Article article = new Article(member, new Title("title"), new Url("url"), new ImageUrl("imageUrl"));
        final LoginMember unLoginMember = new LoginMember(1L, ANONYMOUS);

        when(articleRepository.findArticlesByCourse(any())).thenReturn(Arrays.asList(article));

        //when
        final List<ArticleResponse> articleResponses = articleService.getFilteredArticles(unLoginMember, ArticleFilterType.BACKEND, false);

        //then
        verify(articleRepository).findArticlesByCourse(any());
        Assertions.assertThat(articleResponses.get(0).getTitle()).isEqualTo(article.getTitle().getTitle());
    }

    @DisplayName("로그인 유저가 북마크 백엔드 아티클을 필터링 한다.")
    @Test
    void filter_isBookmarked() {
        //given
        final Member member = new Member(1L, "username", "nickname", CREW, 1L, "url");
        final Article article = new Article(member, new Title("title"), new Url("url"), new ImageUrl("imageUrl"));
        final LoginMember loginMember = new LoginMember(1L, MEMBER);

        when(articleRepository.findArticlesByCourseAndMember(anyString(), anyLong(), anyBoolean())).thenReturn(Arrays.asList(article));

        //when
        final List<ArticleResponse> articleResponses = articleService.getFilteredArticles(loginMember, ArticleFilterType.BACKEND, true);

        //then
        verify(articleRepository).findArticlesByCourseAndMember(anyString(), anyLong(), anyBoolean());
        Assertions.assertThat(articleResponses.get(0).getTitle()).isEqualTo(article.getTitle().getTitle());
    }

    @DisplayName("아티클 조회시 조회수가 증가한다.")
    @Test
    void updateViews() {
        //given
        final Member member = new Member(1L, "username", "nickname", CREW, 1L, "url");
        final Article article = new Article(member, new Title("title"), new Url("url"), new ImageUrl("imageUrl"));
        final Long articleId = 1L;
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        //when
        articleService.updateViewCount(articleId);

        //then
        Assertions.assertThat(article.getViews().getViews()).isEqualTo(1);
    }
}
