package de.assecor.assessment.backend.repository.jpa;

import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public class JpaPersonRepositoryAdapter implements PersonRepository {

  private final JpaPersonRepository jpaRepository;

  public JpaPersonRepositoryAdapter(JpaPersonRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public List<PersonEntity> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<PersonEntity> findById(Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public List<PersonEntity> findByColor(Color color) {
    return jpaRepository.findByColor(color);
  }

  @Override
  public PersonEntity save(PersonEntity entity) {
    return jpaRepository.save(entity);
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaRepository.existsById(id);
  }
}
