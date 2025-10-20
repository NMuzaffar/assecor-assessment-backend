package de.assecor.assessment.backend.mapper;

import de.assecor.assessment.backend.dto.PersonRequestDto;
import de.assecor.assessment.backend.dto.PersonResponseDto;
import de.assecor.assessment.backend.entity.PersonEntity;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  PersonEntity toEntity(PersonRequestDto dto);

  PersonResponseDto toResponseDto(PersonEntity entity);

  List<PersonResponseDto> toResponseDtoList(List<PersonEntity> entities);
}
