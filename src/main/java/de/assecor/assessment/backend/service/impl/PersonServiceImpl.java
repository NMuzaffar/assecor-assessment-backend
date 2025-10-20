package de.assecor.assessment.backend.service.impl;

import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.exception.PersonNotFoundException;
import de.assecor.assessment.backend.mapper.PersonMapper;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.repository.PersonRepository;
import de.assecor.assessment.backend.service.PersonService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;
  private final PersonMapper personMapper;

  public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
    this.personRepository = personRepository;
    this.personMapper = personMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<PersonResponseDto> getAllPersons() {
    List<PersonEntity> entities = personRepository.findAll();
    return personMapper.toResponseDtoList(entities);
  }

  @Override
  @Transactional(readOnly = true)
  public PersonResponseDto getPersonById(Long id) {
    PersonEntity entity =
        personRepository
            .findById(id)
            .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    return personMapper.toResponseDto(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PersonResponseDto> getPersonsByColor(Color color) {
    List<PersonEntity> entities = personRepository.findByColor(color);
    return personMapper.toResponseDtoList(entities);
  }

  @Override
  public PersonResponseDto createPerson(PersonRequestDto requestDto) {
    PersonEntity entity = personMapper.toEntity(requestDto);
    PersonEntity savedEntity = personRepository.save(entity);
    return personMapper.toResponseDto(savedEntity);
  }

  @Override
  public void deletePerson(Long id) {
    if (!personRepository.existsById(id)) {
      throw new PersonNotFoundException("Person not found with id: " + id);
    }
    personRepository.deleteById(id);
  }
}
