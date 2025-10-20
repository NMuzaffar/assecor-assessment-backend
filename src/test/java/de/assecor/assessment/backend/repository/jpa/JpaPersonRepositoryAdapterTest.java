package de.assecor.assessment.backend.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("jpa")
@Import(JpaPersonRepositoryAdapter.class)
public class JpaPersonRepositoryAdapterTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByColor_ShouldReturnMatchingPersons() {
        // Given
        PersonEntity person1 = PersonEntity.builder().name("John").lastname("Doe").color(Color.BLUE).zipcode("12345").city("New York").build();
        entityManager.persist(person1);
        PersonEntity person2 = PersonEntity.builder().name("Jane").lastname("Smith").color(Color.RED).zipcode("54321").city("London").build();
        entityManager.persist(person2);
        PersonEntity person3 = PersonEntity.builder().name("Peter").lastname("Jones").color(Color.BLUE).zipcode("98765").city("Berlin").build();
        entityManager.persist(person3);
        entityManager.flush();

        // When
        List<PersonEntity> bluePersons = personRepository.findByColor(Color.BLUE);

        // Then
        assertThat(bluePersons).hasSize(2).contains(person1, person3);
    }

    @Test
    void save_ShouldPersistPerson() {
        // Given
        PersonEntity newPerson = PersonEntity.builder().name("Test").lastname("Person").color(Color.GREEN).zipcode("11111").city("Testville").build();

        // When
        PersonEntity savedPerson = personRepository.save(newPerson);

        // Then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isNotNull();
        PersonEntity foundPerson = entityManager.find(PersonEntity.class, savedPerson.getId());
        assertThat(foundPerson.getLastname()).isEqualTo("Person");
    }

    @Test
    void findAll_ShouldReturnAllPersons() {
        // Given
        PersonEntity person1 = PersonEntity.builder().name("John").lastname("Doe").color(Color.BLUE).zipcode("12345").city("New York").build();
        entityManager.persist(person1);
        PersonEntity person2 = PersonEntity.builder().name("Jane").lastname("Smith").color(Color.RED).zipcode("54321").city("London").build();
        entityManager.persist(person2);
        entityManager.flush();

        // When
        List<PersonEntity> allPersons = personRepository.findAll();

        // Then
        assertThat(allPersons).hasSize(2).contains(person1, person2);
    }

    @Test
    void findById_WhenPersonExists_ShouldReturnPerson() {
        // Given
        PersonEntity person1 = PersonEntity.builder().name("John").lastname("Doe").color(Color.BLUE).zipcode("12345").city("New York").build();
        PersonEntity persistedPerson = entityManager.persistAndFlush(person1);

        // When
        Optional<PersonEntity> foundPerson = personRepository.findById(persistedPerson.getId());

        // Then
        assertThat(foundPerson).isPresent();
        assertThat(foundPerson.get().getId()).isEqualTo(persistedPerson.getId());
    }

    @Test
    void deleteById_ShouldRemovePerson() {
        // Given
        PersonEntity person1 = PersonEntity.builder().name("John").lastname("Doe").color(Color.BLUE).zipcode("12345").city("New York").build();
        PersonEntity persistedPerson = entityManager.persistAndFlush(person1);
        Long id = persistedPerson.getId();

        // When
        personRepository.deleteById(id);

        // Then
        PersonEntity foundPerson = entityManager.find(PersonEntity.class, id);
        assertThat(foundPerson).isNull();
    }

    @Test
    void existsById_ShouldReturnCorrectStatus() {
        // Given
        PersonEntity person1 = PersonEntity.builder().name("John").lastname("Doe").color(Color.BLUE).zipcode("12345").city("New York").build();
        PersonEntity persistedPerson = entityManager.persistAndFlush(person1);

        // When
        boolean exists = personRepository.existsById(persistedPerson.getId());
        boolean notExists = personRepository.existsById(999L);

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
