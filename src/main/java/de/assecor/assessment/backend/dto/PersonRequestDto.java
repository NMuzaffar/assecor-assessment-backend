package de.assecor.assessment.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.assecor.assessment.backend.model.Color;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequestDto {

  @NotBlank(message = "Name is required")
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
  @JsonProperty("name")
  private String name;

  @NotBlank(message = "Last name is required")
  @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
  @JsonProperty("lastname")
  private String lastname;

  @NotBlank(message = "Zip code is required")
  @Pattern(regexp = "\\d{5}", message = "Zip code must be exactly 5 digits")
  @JsonProperty("zipcode")
  private String zipcode;

  @NotBlank(message = "City is required")
  @Size(min = 1, max = 100, message = "City must be between 1 and 100 characters")
  @JsonProperty("city")
  private String city;

  @NotNull(message = "Favorite color is required")
  @JsonProperty("color")
  private Color color;
}
