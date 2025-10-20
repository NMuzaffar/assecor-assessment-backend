package de.assecor.assessment.backend.converter;

import de.assecor.assessment.backend.model.Color;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ColorEnumConverter implements Converter<String, Color> {

  @Override
  public Color convert(String source) {
    return Color.fromDisplayName(source.toUpperCase());
  }
}
