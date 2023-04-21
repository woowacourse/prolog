package wooteco.prolog.badge.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplimentKingBadgeCreatorTest {

    @Mock
    private BadgeRepository badgeRepository;

    private ComplimentKingBadgeCreator complimentKingBadgeCreator;

    @BeforeEach
    void init() {
        complimentKingBadgeCreator =
            new ComplimentKingBadgeCreator(badgeRepository, List.of(1L, 2L));
    }

    @DisplayName("일정 session동안 특정한 칭찬 개수 초과시 칭찬 뱃지를 만들 수 있다")
    @Test
    void test_create() {
        //given
        final String userName = "urrr";

        //when
        when(badgeRepository.countLikesByUsernameDuringSessions(any(), any()))
            .thenReturn(15);

        //optional >> nullable할 수 있다는 것을 알려줌
        Optional<BadgeType> badgeType = complimentKingBadgeCreator.create(userName);

        //then
        assertThat(badgeType).isPresent();
        assertThat(badgeType.get()).isEqualTo(BadgeType.COMPLIMENT_KING);
    }

    @DisplayName("일정 session동안 특정한 칭찬 개수 미만일시 칭찬 뱃지를 만들 수 없다")
    @Test
    void test_create_exception() {
        //given
        final String username = "judy";

        //when
        when(badgeRepository.countLikesByUsernameDuringSessions(any(), any()))
            .thenReturn(14);

        Optional<BadgeType> badgeType = complimentKingBadgeCreator.create(username);

        //then
        assertThat(badgeType).isEmpty();
    }
}
