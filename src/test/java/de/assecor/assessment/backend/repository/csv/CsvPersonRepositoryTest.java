package de.assecor.assessment.backend.repository.csv;

import static org.junit.jupiter.api.Assertions.*;

import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CsvPersonRepositoryTest {

    private CsvPersonRepository csvPersonRepository;

    @BeforeEach
    void setUp() throws Exception {
        Resource testCsv = new ClassPathResource("test-persons.csv");
        csvPersonRepository = new CsvPersonRepository(testCsv);
        csvPersonRepository.afterPropertiesSet();
    }

    @Test
    void loadPersons_ShouldLoadAndParseCsv() {
        // When
        List<PersonEntity> persons = csvPersonRepository.findAll();

        // Then
        assertEquals(3, persons.size());
        assertEquals("Doe", persons.get(0).getLastname());
        assertEquals("John", persons.get(0).getName());
        assertEquals(Color.BLUE, persons.get(0).getColor());
    }

    @Test
    void findById_WhenPersonExists_ShouldReturnPerson() {
        // When
        Optional<PersonEntity> person = csvPersonRepository.findById(1L);

        // Then
        assertTrue(person.isPresent());
        assertEquals("Doe", person.get().getLastname());
    }

    @Test
    void findById_WhenPersonDoesNotExist_ShouldReturnEmpty() {
        // When
        Optional<PersonEntity> person = csvPersonRepository.findById(99L);

        // Then
        assertFalse(person.isPresent());
    }

    @Test
    void findByColor_ShouldReturnMatchingPersons() {
        // When
        List<PersonEntity> bluePersons = csvPersonRepository.findByColor(Color.BLUE);
        List<PersonEntity> redPersons = csvPersonRepository.findByColor(Color.RED);

        // Then
        assertEquals(1, bluePersons.size());
        assertEquals("Doe", bluePersons.get(0).getLastname());
        assertEquals(1, redPersons.size());
        assertEquals("Smith", redPersons.get(0).getLastname());
    }

    @Test
    void save_WhenNewPerson_ShouldAssignIdAndSave() {
        // Given
        PersonEntity newPerson = PersonEntity.builder()
                .name("Test")
                .lastname("Person")
                .zipcode("11111")
                .city("Testville")
                .color(Color.GREEN)
                .build();

        // When
        PersonEntity savedPerson = csvPersonRepository.save(newPerson);

        // Then
        assertNotNull(savedPerson.getId());
        assertEquals(4L, savedPerson.getId());
        Optional<PersonEntity> foundPerson = csvPersonRepository.findById(4L);
        assertTrue(foundPerson.isPresent());
        assertEquals("Person", foundPerson.get().getLastname());
    }

    @Test
    void deleteById_ShouldRemovePerson() {
        // When
        csvPersonRepository.deleteById(1L);

        // Then
        assertFalse(csvPersonRepository.findById(1L).isPresent());
    }

    @Test
    void existsById_ShouldReturnCorrectStatus() {
        // Then
        assertTrue(csvPersonRepository.existsById(1L));
        assertFalse(csvPersonRepository.existsById(99L));
    }
}
