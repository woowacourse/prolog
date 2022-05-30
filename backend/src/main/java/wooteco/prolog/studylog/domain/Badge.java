package wooteco.prolog.studylog.domain;

public class Badge {

  private final String imageUrl;
  private final String name;

  public Badge(String imageUrl, String name) {
    this.imageUrl = imageUrl;
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getName() {
    return name;
  }
}
