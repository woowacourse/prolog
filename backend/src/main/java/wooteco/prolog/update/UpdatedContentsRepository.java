package wooteco.prolog.update;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UpdatedContentsRepository extends JpaRepository<UpdatedContents, Long> {

    @Query("select uc from UpdatedContents uc where uc.updateContent = :content")
    Optional<UpdatedContents> findByContent(@Param("content") UpdateContent content);
}
