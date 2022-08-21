package wooteco.prolog.levellogs.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.login.excetpion.StudylogTitleNullOrEmptyException;
import wooteco.prolog.studylog.exception.TooLongTitleException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Title {

	public static final int MAX_LENGTH = 50;

	@Column(nullable = false, length = MAX_LENGTH)
	private String title;

	public Title(String title) {
		this.title = trim(title);
		validateNull(title);
		validateEmpty(title);
		validateOnlyBlank(title);
		validateMaxLength(title);
	}

	private String trim(String name) {
		return name.trim();
	}

	private void validateNull(String title) {
		if (Objects.isNull(title)) {
			throw new StudylogTitleNullOrEmptyException();
		}
	}

	private void validateEmpty(String title) {
		if (title.isEmpty()) {
			throw new StudylogTitleNullOrEmptyException();
		}
	}

	private void validateOnlyBlank(String title) {
		if (title.trim().isEmpty()) {
			throw new StudylogTitleNullOrEmptyException();
		}
	}

	private void validateMaxLength(String title) {
		if (title.length() > MAX_LENGTH) {
			throw new TooLongTitleException();
		}
	}
}
