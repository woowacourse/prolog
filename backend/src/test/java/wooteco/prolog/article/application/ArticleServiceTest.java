package wooteco.prolog.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.login.ui.LoginMember.Authority.MEMBER;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.article.application.OgTagParser.OgType;
import wooteco.prolog.article.domain.Article;
import wooteco.prolog.article.domain.ImageUrl;
import wooteco.prolog.article.domain.Title;
import wooteco.prolog.article.domain.Url;
import wooteco.prolog.article.domain.repository.ArticleRepository;
import wooteco.prolog.article.ui.ArticleRequest;
import wooteco.prolog.article.ui.ArticleUrlRequest;
import wooteco.prolog.article.ui.ArticleUrlResponse;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private OgTagParser ogTagParser;

    @InjectMocks
    private ArticleService articleService;

    @DisplayName("아티클을 생성한다.")
    @Test
    void create_success() {
        //given
        final ArticleRequest judyRequest = new ArticleRequest("title", "url", "imageUrl");
        final Member member = new Member(1L, "username", "nickname", Role.CREW, 1L, "url");
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

    @DisplayName("아티클을 수정한다.")
    @Test
    void update_success() {
        //given
        final Member judy = new Member(1L, "username", "nickname", Role.CREW, 1L, "url");
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
        when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(null));

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
        final Member judy = new Member(1L, "judith", "judy", Role.CREW, 1L, "judyUrl");
        final Member brown = new Member(2L, "brown", "brownie", Role.CREW, 2L, "brownUrl");
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
        final Member judy = new Member(1L, "judith", "judy", Role.CREW, 1L, "judyUrl");
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
        when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);

        //when, then
        assertThatThrownBy(() -> articleService.delete(1L, judyLogin))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("삭제시 아티클 작성자가 아니라면 예외를 발생시킨다.")
    @Test
    void delete_fail_InvalidArticleAuthorException() {
        //given
        final Member judy = new Member(1L, "judith", "judy", Role.CREW, 1L, "judyUrl");
        final Member brown = new Member(2L, "brown", "brownie", Role.CREW, 2L, "brownUrl");
        final Article brownArticle = new Article(brown, new Title("brownTitle"),
            new Url("brownUrl"), new ImageUrl("imageUrl"));
        when(articleRepository.findById(any())).thenReturn(Optional.of(brownArticle));

        final LoginMember judyLogin = new LoginMember(1L, MEMBER);
        when(memberService.findById(any())).thenReturn(judy);

        //when, then
        assertThatThrownBy(() -> articleService.delete(1L, judyLogin))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("링크에서 추출한 제목, 이미지를 반환한다.")
    @Test
    void parse() {
        //given
        final ArticleUrlRequest request = new ArticleUrlRequest("https://www.woowahan.com/");

        final Map<OgType, String> expectedParsedValue = new HashMap<>();
        expectedParsedValue.put(OgType.IMAGE, "이미지");
        expectedParsedValue.put(OgType.TITLE, "제목");

        when(ogTagParser.parse(any())).thenReturn(expectedParsedValue);

        //when
        final ArticleUrlResponse response = articleService.parse(request);

        //then
        assertThat(response.getTitle()).isEqualTo("제목");
        assertThat(response.getImageUrl()).isEqualTo("이미지");
    }
}
