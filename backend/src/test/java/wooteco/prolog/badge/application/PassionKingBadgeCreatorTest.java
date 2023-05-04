package wooteco.prolog.badge.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@ExtendWith(MockitoExtension.class)
class PassionKingBadgeCreatorTest {

    @Mock
    private BadgeRepository badgeRepository;
    private PassionKingBadgeCreator passionKingBadgeCreator;

    @BeforeEach
    void init() {
        passionKingBadgeCreator =
            new PassionKingBadgeCreator(badgeRepository, Arrays.asList(1L, 2L));
    }

    @DisplayName("create() : 어떠한 사용자의 일정 session동안 특정한 개수 이상만큼 글을 작성하면 열정 뱃지를 만들 수 있다")
    @Test
    void create() {
        //given
        final String userName = "judy";

        when(badgeRepository.countStudylogByUsernameDuringSessions(any(), any()))
            .thenReturn(7);

        //when
        Optional<BadgeType> badgeType = passionKingBadgeCreator.create(userName);

        //then
        assertThat(badgeType).isPresent();
        assertThat(badgeType.get()).isEqualTo(BadgeType.PASSION_KING);
    }

    @DisplayName("create() : 어떠한 사용자의 일정 session동안 특정한 개수 미만만큼 글을 작성했다면 열정 뱃지를 만들 수 없다")
    @Test
    void create_exception() {
        //given
        final String userName = "judy";

        when(badgeRepository.countStudylogByUsernameDuringSessions(any(), any()))
            .thenReturn(6);

        //when
        Optional<BadgeType> badgeType = passionKingBadgeCreator.create(userName);

        //then
        assertThat(badgeType).isEmpty();
    }
}
