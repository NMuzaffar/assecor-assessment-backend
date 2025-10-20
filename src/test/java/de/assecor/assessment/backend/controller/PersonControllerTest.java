package de.assecor.assessment.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.exception.PersonNotFoundException;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.service.PersonService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getPersons_ShouldReturnListOfPersons() throws Exception {
    PersonResponseDto person1 =
        new PersonResponseDto(1L, "Doe", "John", "12345", "New York", Color.BLUE);
    List<PersonResponseDto> persons = List.of(person1);

    given(personService.getAllPersons()).willReturn(persons);

    mockMvc
        .perform(get("/persons"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(persons.size()));
  }

  @Test
  public void getPersonById_ShouldReturnPerson() throws Exception {
    long personId = 1L;
    PersonResponseDto person =
        new PersonResponseDto(personId, "Doe", "John", "12345", "New York", Color.BLUE);

    given(personService.getPersonById(personId)).willReturn(person);

    mockMvc
        .perform(get("/persons/{id}", personId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(person.getId()));
  }

  @Test
  void getPersonById_WhenPersonNotFound_ShouldReturnNotFound() throws Exception {
    long personId = 99L;
    given(personService.getPersonById(personId))
        .willThrow(new PersonNotFoundException("Person not found with id: " + personId));

    mockMvc
        .perform(get("/persons/{id}", personId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Person not found with id: " + personId));
  }

  @Test
  public void getPersonsByColor_ShouldReturnListOfPersons() throws Exception {
    Color colorEnum = Color.BLUE;
    String colorName = colorEnum.getDisplayName();
    PersonResponseDto person1 =
        new PersonResponseDto(1L, "Doe", "John", "12345", "New York", colorEnum);
    List<PersonResponseDto> persons = List.of(person1);

    given(personService.getPersonsByColor(colorEnum)).willReturn(persons);

    mockMvc
        .perform(get("/persons/color/{color}", colorName))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(persons.size()));
  }

  @Test
  void getPersonsByColor_WhenColorNameIsInvalid_ShouldReturnNotFound() throws Exception {
    String invalidColorName = "invalid-color";

    // This test assumes the controller calls Color.fromDisplayName, which throws ColorNotFoundException.
    // The GlobalExceptionHandler then converts this to a 404 response.
    mockMvc
        .perform(get("/persons/color/{color}", invalidColorName))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Unknown color: " + invalidColorName.toUpperCase()));
  }

  @Test
  public void createPerson_ShouldReturnCreatedPerson() throws Exception {
    PersonRequestDto personToCreate =
        PersonRequestDto.builder()
            .name("John")
            .lastname("Doe")
            .zipcode("12345")
            .city("New York")
            .color(Color.BLUE)
            .build();

    PersonResponseDto createdPerson =
        new PersonResponseDto(1L, "John", "Doe", "12345", "New York", Color.BLUE);

    given(personService.createPerson(any(PersonRequestDto.class))).willReturn(createdPerson);

    mockMvc
        .perform(
            post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personToCreate)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(createdPerson.getId()));
  }

  @Test
  void createPerson_WhenNameIsBlank_ShouldReturnBadRequest() throws Exception {
    PersonRequestDto request = PersonRequestDto.builder()
        .lastname("Doe")
        .zipcode("12345")
        .city("New York")
        .color(Color.BLUE)
        .build();

    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.name").value("Name is required"));
  }

  @Test
  void createPerson_WhenZipcodeIsInvalid_ShouldReturnBadRequest() throws Exception {
    PersonRequestDto request = PersonRequestDto.builder()
        .name("John")
        .lastname("Doe")
        .zipcode("123") // Invalid
        .city("New York")
        .color(Color.BLUE)
        .build();

    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.zipcode").value("Zip code must be exactly 5 digits"));
  }

  @Test
  void createPerson_WhenColorIsNull_ShouldReturnBadRequest() throws Exception {
    PersonRequestDto request = PersonRequestDto.builder()
        .name("John")
        .lastname("Doe")
        .zipcode("12345")
        .city("New York")
        .color(null) // Invalid
        .build();

    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.color").value("Favorite color is required"));
  }

  @Test
  void deletePerson_ShouldReturnNoContent() throws Exception {
    long personId = 1L;

    doNothing().when(personService).deletePerson(personId);

    mockMvc.perform(delete("/persons/{id}", personId)).andExpect(status().isNoContent());

    verify(personService, times(1)).deletePerson(personId);
  }
}
