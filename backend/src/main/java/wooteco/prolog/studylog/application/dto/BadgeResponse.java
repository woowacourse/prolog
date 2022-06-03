package wooteco.prolog.studylog.application.dto;

import java.util.Objects;

public class BadgeResponse {

  private final String imageUrl;
  private final String name;

  public BadgeResponse(String imageUrl, String name) {
    this.imageUrl = imageUrl;
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadgeResponse that = (BadgeResponse) o;
    return Objects.equals(imageUrl, that.imageUrl) && Objects.equals(name,
        that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imageUrl, name);
  }
}
