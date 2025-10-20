package de.assecor.assessment.backend.repository.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import de.assecor.assessment.backend.repository.PersonRepository;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Primary
@Profile("!jpa")
@Repository
@Slf4j
public class CsvPersonRepository implements PersonRepository, InitializingBean {

  private final Resource csvDatasource;
  private final ConcurrentMap<Long, PersonEntity> personStore;
  private final AtomicLong idGenerator;

  public CsvPersonRepository(@Value("${datasource.csv.file}") Resource csvDatasource) {
    this.csvDatasource = csvDatasource;
    this.personStore = new ConcurrentHashMap<>();
    this.idGenerator = new AtomicLong(1L);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    loadPersons();
  }

  @Override
  public List<PersonEntity> loadPersons() {
    try (CSVReader reader = new CSVReader(new FileReader(csvDatasource.getFile()))) {
      final List<PersonEntity> persons = new ArrayList<>();
      List<String[]> records = reader.readAll();
      for (int i = 0; i < records.size(); i++) {
        String[] record = records.get(i);
        if (record.length < 4) {
          log.error("Skipping malformed row: {}", Arrays.toString(record));
          continue;
        }
        try {
          final String[] address = record[2].trim().split(" ");
          PersonEntity entity =
              PersonEntity.builder()
                  .id((long) (i + 1))
                  .name(record[1].trim())
                  .lastname(record[0].trim())
                  .zipcode(address[0].trim())
                  .city(address[1].trim())
                  .color(Color.fromId(Integer.parseInt(record[3].trim())))
                  .createdAt(LocalDateTime.now())
                  .updatedAt(LocalDateTime.now())
                  .build();
          personStore.put(entity.getId(), entity);
          persons.add(entity);
          idGenerator.set(i + 1);
        } catch (NumberFormatException e) {
          System.err.println("Could not parse colorId on row " + (i + 1));
        }
      }

      return persons;
    } catch (IOException | CsvException e) {
      log.error("Failed to load CSV data", e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<PersonEntity> findAll() {
    return new ArrayList<>(personStore.values());
  }

  @Override
  public Optional<PersonEntity> findById(Long id) {
    return Optional.ofNullable(personStore.get(id));
  }

  @Override
  public List<PersonEntity> findByColor(Color color) {
    return personStore.values().stream()
        .filter(p -> p.getColor() == color)
        .collect(Collectors.toList());
  }

  @Override
  public PersonEntity save(PersonEntity entity) {
    if (entity.getId() == null) {
      entity.setId(idGenerator.incrementAndGet());
      entity.setCreatedAt(LocalDateTime.now());
    }
    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(LocalDateTime.now());
    }
    entity.setUpdatedAt(LocalDateTime.now());
    personStore.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public void deleteById(Long id) {
    personStore.remove(id);
  }

  @Override
  public boolean existsById(Long id) {
    return personStore.containsKey(id);
  }
}
