package swc3.server2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server2.model.Tutorial;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByTitleContaining(String title);
    Page<Tutorial> findByTitleContaining(String title, Pageable paging);
    List<Tutorial> findByPublished(boolean published);
}
