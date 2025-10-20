package de.assecor.assessment.backend.service;

import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.model.Color;
import java.util.List;

public interface PersonService {

  List<PersonResponseDto> getAllPersons();

  PersonResponseDto getPersonById(Long id);

  List<PersonResponseDto> getPersonsByColor(Color color);

  PersonResponseDto createPerson(PersonRequestDto requestDto);

  void deletePerson(Long id);
}
