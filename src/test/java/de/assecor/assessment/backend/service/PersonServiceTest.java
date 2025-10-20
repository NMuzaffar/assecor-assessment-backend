package de.assecor.assessment.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.exception.PersonNotFoundException;
import de.assecor.assessment.backend.mapper.PersonMapper;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.repository.PersonRepository;
import de.assecor.assessment.backend.service.impl.PersonServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    private PersonEntity personEntity;
    private PersonResponseDto personResponseDto;
    private PersonRequestDto personRequestDto;

    @BeforeEach
    void setUp() {
        personEntity = new PersonEntity();
        personEntity.setId(1L);
        personEntity.setName("John");
        personEntity.setLastname("Doe");
        personEntity.setZipcode("12345");
        personEntity.setCity("New York");
        personEntity.setColor(Color.BLUE);

        personResponseDto = new PersonResponseDto(1L, "John", "Doe", "12345", "New York", Color.BLUE);

        personRequestDto = PersonRequestDto.builder()
                .name("John")
                .lastname("Doe")
                .zipcode("12345")
                .city("New York")
                .color(Color.BLUE)
                .build();
    }

    @Test
    void getAllPersons_ShouldReturnPersonList() {
        // Given
        when(personRepository.findAll()).thenReturn(Collections.singletonList(personEntity));
        when(personMapper.toResponseDtoList(anyList())).thenReturn(Collections.singletonList(personResponseDto));

        // When
        List<PersonResponseDto> result = personService.getAllPersons();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(personResponseDto, result.get(0));
        verify(personRepository, times(1)).findAll();
        verify(personMapper, times(1)).toResponseDtoList(anyList());
    }

    @Test
    void getPersonById_WhenPersonExists_ShouldReturnPerson() {
        // Given
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(personMapper.toResponseDto(personEntity)).thenReturn(personResponseDto);

        // When
        PersonResponseDto result = personService.getPersonById(1L);

        // Then
        assertNotNull(result);
        assertEquals(personResponseDto, result);
        verify(personRepository, times(1)).findById(1L);
        verify(personMapper, times(1)).toResponseDto(personEntity);
    }

    @Test
    void getPersonById_WhenPersonDoesNotExist_ShouldThrowException() {
        // Given
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(1L));
        verify(personRepository, times(1)).findById(1L);
        verify(personMapper, never()).toResponseDto(any(PersonEntity.class));
    }

    @Test
    void getPersonsByColor_ShouldReturnPersonList() {
        // Given
        Color color = Color.BLUE;
        when(personRepository.findByColor(color)).thenReturn(Collections.singletonList(personEntity));
        when(personMapper.toResponseDtoList(anyList())).thenReturn(Collections.singletonList(personResponseDto));

        // When
        List<PersonResponseDto> result = personService.getPersonsByColor(color);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(personResponseDto, result.get(0));
        verify(personRepository, times(1)).findByColor(color);
        verify(personMapper, times(1)).toResponseDtoList(anyList());
    }

    @Test
    void createPerson_ShouldSaveAndReturnPerson() {
        // Given
        when(personMapper.toEntity(personRequestDto)).thenReturn(personEntity);
        when(personRepository.save(personEntity)).thenReturn(personEntity);
        when(personMapper.toResponseDto(personEntity)).thenReturn(personResponseDto);

        // When
        PersonResponseDto result = personService.createPerson(personRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(personResponseDto, result);
        verify(personMapper, times(1)).toEntity(personRequestDto);
        verify(personRepository, times(1)).save(personEntity);
        verify(personMapper, times(1)).toResponseDto(personEntity);
    }

    @Test
    void deletePerson_WhenPersonExists_ShouldDeletePerson() {
        // Given
        when(personRepository.existsById(1L)).thenReturn(true);
        doNothing().when(personRepository).deleteById(1L);

        // When
        personService.deletePerson(1L);

        // Then
        verify(personRepository, times(1)).existsById(1L);
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePerson_WhenPersonDoesNotExist_ShouldThrowException() {
        // Given
        when(personRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(PersonNotFoundException.class, () -> personService.deletePerson(1L));
        verify(personRepository, times(1)).existsById(1L);
        verify(personRepository, never()).deleteById(anyLong());
    }
}
