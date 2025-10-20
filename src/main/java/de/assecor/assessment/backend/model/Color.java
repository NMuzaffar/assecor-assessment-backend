package de.assecor.assessment.backend.model;

import com.fasterxml.jackson.annotation.JsonValue;
import de.assecor.assessment.backend.exception.ColorNotFoundException;

public enum Color {
  BLUE(1, "blau"),
  GREEN(2, "grün"),
  VIOLET(3, "violett"),
  RED(4, "rot"),
  YELLOW(5, "gelb"),
  TURQUOISE(6, "türkis"),
  WHITE(7, "weiß");

  private final int id;
  private final String displayName;

  Color(int id, String name) {
    this.id = id;
    this.displayName = name;
  }

  public int getId() {
    return id;
  }

  @JsonValue
  public String getDisplayName() {
    return displayName;
  }

  /** Find Color by ID */
  public static Color fromId(int id) {
    for (Color color : values()) {
      if (color.getId() == id) {
        return color;
      }
    }
    throw new ColorNotFoundException("Unknown color ID: " + id);
  }

  /** Find Color by German name (case-insensitive) */
  public static Color fromDisplayName(String name) {
    for (Color color : values()) {
      if (color.getDisplayName().equalsIgnoreCase(name)) {
        return color;
      }
    }
    throw new ColorNotFoundException("Unknown color: " + name);
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
