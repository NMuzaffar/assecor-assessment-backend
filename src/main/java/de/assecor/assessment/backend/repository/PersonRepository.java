package de.assecor.assessment.backend.repository;

import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {

  List<PersonEntity> findAll();

  Optional<PersonEntity> findById(Long id);

  List<PersonEntity> findByColor(Color color);

  default List<PersonEntity> loadPersons() {
      return Collections.emptyList();
  }

  PersonEntity save(PersonEntity person);

  void deleteById(Long id);

  boolean existsById(Long id);
}
