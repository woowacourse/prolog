package wooteco.prolog.organization.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.organization.domain.repository.OrganizationGroupMemberRepository;

@Transactional
@SpringBootTest
public class OrganizationGroupMemberRepositoryTest {

    @Autowired
    private OrganizationGroupMemberRepository repository;

    @Test
    public void existsByUsername_shouldReturnTrue_whenUsernameExists() {
        // given
        String username = "existingUser";
        OrganizationGroupMember member = new OrganizationGroupMember(1L, username, "nickname");
        repository.save(member);

        // when
        boolean exists = repository.existsByUsername(username);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByUsername_shouldReturnFalse_whenUsernameDoesNotExist() {
        // given
        String username = "nonExistingUser";

        // when
        boolean exists = repository.existsByUsername(username);

        // then
        assertThat(exists).isFalse();
    }
}
