package wooteco.prolog.fixtures;

import wooteco.prolog.article.ui.ArticleRequest;
import wooteco.prolog.article.ui.ArticleUrlRequest;

public class ArticleFixture {

    public static final ArticleRequest ARTICLE_REQUEST1 = new ArticleRequest("첫 아티클 제목", "첫 아티클 주소",
        "이미지 URL");
    public static final ArticleRequest ARTICLE_REQUEST2 = new ArticleRequest("두번째 아티클 제목",
        "두번째 아티클 주소", "이미지 URL");
    public static final ArticleUrlRequest ARTICLE_URL_REQUEST = new ArticleUrlRequest(
        "https://www.woowahan.com/");
}
