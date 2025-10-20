package de.assecor.assessment.backend.dto;

public record PersonDto(long id,
                        String name,
                        String lastname,
                        String zipcode,
                        String city,
                        String color) {}
