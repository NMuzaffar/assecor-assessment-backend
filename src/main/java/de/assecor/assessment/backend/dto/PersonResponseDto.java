package de.assecor.assessment.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.assecor.assessment.backend.model.Color;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponseDto {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("lastname")
  private String lastname;

  @JsonProperty("zipcode")
  private String zipcode;

  @JsonProperty("city")
  private String city;

  @JsonProperty("color")
  private Color color;
}
