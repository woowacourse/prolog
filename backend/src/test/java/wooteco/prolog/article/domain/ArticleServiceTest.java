package wooteco.prolog.article.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import wooteco.prolog.article.domain.repository.ArticleRepository;
import wooteco.prolog.article.ui.ArticleRequest;
import wooteco.prolog.article.ui.ArticleResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.support.utils.RepositoryTest;

import java.util.List;
import java.util.Optional;

import static wooteco.prolog.login.ui.LoginMember.Authority.MEMBER;

@ExtendWith(MockitoExtension.class)
@TestExecutionListeners(value = AcceptanceTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@RepositoryTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("아티클을 생성한다.")
    @Test
    void create_success() {
        //given
        ArticleRequest judyRequest = new ArticleRequest("title", "url");
        memberRepository.save(new Member(1L, "username", "nickname", Role.CREW, 1L, "url"));
        LoginMember judyLogin = new LoginMember(1L, MEMBER);

        //when
        Long id = articleService.create(judyRequest, judyLogin);

        //then
        Assertions.assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("아티클을 조회한다.")
    void getAll_success() {
        //given
        ArticleRequest judyRequest = new ArticleRequest("title", "url");
        memberRepository.save(new Member(1L, "judith", "judy", Role.CREW, 1L, "url"));
        LoginMember judyLogin = new LoginMember(1L, MEMBER);
        articleService.create(judyRequest, judyLogin);

        ArticleRequest brownRequest = new ArticleRequest("title2", "url2");
        memberRepository.save(new Member(2L, "brownie", "brown", Role.CREW, 2L, "url2"));
        LoginMember brownLogin = new LoginMember(2L, MEMBER);
        articleService.create(brownRequest, brownLogin);

        //when
        List<ArticleResponse> responses = articleService.getAll();

        //then
        Assertions.assertThat(responses).size().isEqualTo(2);
    }

    @DisplayName("아티클을 수정한다.")
    @Test
    void update_success() {
        //given
        ArticleRequest judyRequest = new ArticleRequest("title", "url");
        memberRepository.save(new Member(1L, "judith", "judy", Role.CREW, 1L, "url"));
        LoginMember judyLogin = new LoginMember(1L, MEMBER);
        articleService.create(judyRequest, judyLogin);
        ArticleRequest judyChangedRequest = new ArticleRequest("title", "changedUrl");

        //when
        articleService.update(1L, judyChangedRequest, judyLogin);

        //then
        Optional<Article> article = articleRepository.findById(1L);
        Assertions.assertThat(article.isPresent()).isTrue();
        Assertions.assertThat(article.get().getUrl().getUrl()).isEqualTo("changedUrl");
    }

    @DisplayName("아티클을 삭제한다.")
    @Test
    void delete_success() {
        //given
        ArticleRequest judyRequest = new ArticleRequest("title", "url");
        memberRepository.save(new Member(1L, "judith", "judy", Role.CREW, 1L, "url"));
        LoginMember judyLogin = new LoginMember(1L, MEMBER);
        articleService.create(judyRequest, judyLogin);

        //when
        articleService.delete(1L, judyLogin);

        //then
        Optional<Article> article = articleRepository.findById(1L);
        Assertions.assertThat(article.isPresent()).isFalse();
    }
}
