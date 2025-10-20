package de.assecor.assessment.backend.controller;

import de.assecor.assessment.backend.dto.PersonDto;
import de.assecor.assessment.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById() {
        return null;

    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody ) {
        return null;
    }
}
