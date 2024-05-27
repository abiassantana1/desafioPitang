package com.desafio.java.pitang.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarroDTO {

    private Long id;
    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private Integer year;
    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String licensePlate;
    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String model;
    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String color;
}
