package de.assecor.assessment.backend.repository.jpa;

import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface JpaPersonRepository extends JpaRepository<PersonEntity, Long> {
  List<PersonEntity> findByColor(Color color);
}
