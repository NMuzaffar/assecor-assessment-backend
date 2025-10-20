package de.assecor.assessment.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
  blau(1),
  gruen(2),
  violet(3),
  rot(4),
  gelb(5),
  tuerkis(6),
  weiss(7);

  private final int colorId;
}
