package de.assecor.assessment.backend.dto;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    
    private Instant timestamp;
    
    private int status;
    
    private String error;
    
    private String message;
    
    private Map<String, String> errors;
}
