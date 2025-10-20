package de.assecor.assessment.backend.controller;

import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.service.PersonService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping
  public ResponseEntity<List<PersonResponseDto>> getAllPersons() {
    List<PersonResponseDto> persons = personService.getAllPersons();
    return ResponseEntity.ok(persons);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PersonResponseDto> getPersonById(@PathVariable Long id) {
    PersonResponseDto person = personService.getPersonById(id);
    return ResponseEntity.ok(person);
  }

  @GetMapping("/color/{color}")
  public ResponseEntity<List<PersonResponseDto>> getPersonsByColor(@PathVariable("color") Color color) {
    List<PersonResponseDto> persons = personService.getPersonsByColor(color);
    return ResponseEntity.ok(persons);
  }

  @PostMapping
  public ResponseEntity<PersonResponseDto> createPerson(@Valid @RequestBody PersonRequestDto requestDto) {
    PersonResponseDto created = personService.createPerson(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
    personService.deletePerson(id);
    return ResponseEntity.noContent().build();
  }
}
