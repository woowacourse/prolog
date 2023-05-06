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

    @DisplayName("create() : 어떠한 사용자의 일정 session동안 7개 이상의 글을 작성하면 열정 뱃지를 만들 수 있다")
    @Test
    void create() {
        //given
        final String userName = "judy";
        final int passionKingCriteria = 7;

        when(badgeRepository.countStudylogByUsernameDuringSessions(any(), any()))
            .thenReturn(passionKingCriteria);

        //when
        Optional<BadgeType> badgeType = passionKingBadgeCreator.create(userName);

        //then
        assertThat(badgeType).contains(BadgeType.PASSION_KING);
    }

    @DisplayName("create() : 어떠한 사용자의 일정 session동안 7개 미만의 글을 작성했다면 열정 뱃지를 만들 수 없다")
    @Test
    void create_exception() {
        //given
        final String userName = "judy";
        final int passionKingLowerCriteria = 6;

        when(badgeRepository.countStudylogByUsernameDuringSessions(any(), any()))
            .thenReturn(passionKingLowerCriteria);

        //when
        Optional<BadgeType> badgeType = passionKingBadgeCreator.create(userName);

        //then
        assertThat(badgeType).isEmpty();
    }
}
