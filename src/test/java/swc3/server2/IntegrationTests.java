package swc3.server2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import swc3.server2.model.Tutorial;
import swc3.server2.repository.TutorialRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTests {

    @Autowired
    TutorialRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    public void should_find_no_tutorials_if_repository_is_empty(){
        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

    //this could be in 3 separate tests
    @Test
    public void should_store_tutorial(){
        Tutorial tutorial = repository.save(new Tutorial("t1", "d1", false));
        assertThat(tutorial).hasFieldOrPropertyWithValue("title", "t1");
        assertThat(tutorial).hasFieldOrPropertyWithValue("description", "d1");
        assertThat(tutorial).hasFieldOrPropertyWithValue("published", false);
    }

    @Test
    public void should_find_all_tutorials(){
        repository.save(new Tutorial("t1", "d1", false));
        repository.save(new Tutorial("t2", "d2", false));
        repository.save(new Tutorial("t3", "d3", false));

        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).hasSize(3);
    }

    //homework - integration tests: ---------------------------------------------------------------------
    @Test
    public void should_find_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);

        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);

        Tutorial foundTutorial = repository.findById(tut2.getId()).get();

        assertThat(foundTutorial).isEqualTo(tut2);
    }

    @Test
    public void should_find_published_tutorials() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);

        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);

        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        entityManager.persist(tut3);

        Iterable<Tutorial> tutorials = repository.findByPublished(true);

        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    public void should_find_tutorials_by_title_containing_string() {
        Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
        entityManager.persist(tut1);

        Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
        entityManager.persist(tut2);

        Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
        entityManager.persist(tut3);

        Iterable<Tutorial> tutorials = repository.findByTitleContaining("ring");

        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    public void should_update_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);

        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);

        Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);

        Tutorial tut = repository.findById(tut2.getId()).get();
        tut.setTitle(updatedTut.getTitle());
        tut.setDescription(updatedTut.getDescription());
        tut.setPublished(updatedTut.getPublished());
        repository.save(tut);

        Tutorial checkTut = repository.findById(tut2.getId()).get();

        assertThat(checkTut.getId()).isEqualTo(tut2.getId());
        assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
        assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
        assertThat(checkTut.getPublished()).isEqualTo(updatedTut.getPublished());
    }

    @Test
    public void should_delete_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);

        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);

        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        entityManager.persist(tut3);

        repository.deleteById(tut2.getId());

        Iterable<Tutorial> tutorials = repository.findAll();

        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    public void should_delete_all_tutorials() {
        entityManager.persist(new Tutorial("Tut#1", "Desc#1", true));
        entityManager.persist(new Tutorial("Tut#2", "Desc#2", false));

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }





}
