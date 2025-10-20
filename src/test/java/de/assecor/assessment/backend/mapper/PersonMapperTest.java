package de.assecor.assessment.backend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.entity.PersonEntity;
import de.assecor.assessment.backend.model.Color;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class PersonMapperTest {

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void toEntity_ShouldMapRequestDtoToEntity() {
        // Given
        PersonRequestDto dto = PersonRequestDto.builder()
                .name("John")
                .lastname("Doe")
                .zipcode("12345")
                .city("New York")
                .color(Color.BLUE)
                .build();

        // When
        PersonEntity entity = personMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getLastname(), entity.getLastname());
        assertEquals(dto.getZipcode(), entity.getZipcode());
        assertEquals(dto.getCity(), entity.getCity());
        assertEquals(dto.getColor(), entity.getColor());
    }

    @Test
    void toResponseDto_ShouldMapEntityToResponseDto() {
        // Given
        PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("Jane");
        entity.setLastname("Smith");
        entity.setZipcode("54321");
        entity.setCity("London");
        entity.setColor(Color.RED);

        // When
        PersonResponseDto dto = personMapper.toResponseDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getLastname(), dto.getLastname());
        assertEquals(entity.getZipcode(), dto.getZipcode());
        assertEquals(entity.getCity(), dto.getCity());
        assertEquals(entity.getColor(), dto.getColor());
    }

    @Test
    void toResponseDtoList_ShouldMapEntityListToResponseDtoList() {
        // Given
        PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("Jane");
        entity.setLastname("Smith");
        entity.setColor(Color.RED);
        List<PersonEntity> entities = Collections.singletonList(entity);

        // When
        List<PersonResponseDto> dtoList = personMapper.toResponseDtoList(entities);

        // Then
        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertEquals(entity.getId(), dtoList.get(0).getId());
    }
}
