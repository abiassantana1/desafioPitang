package com.desafio.java.pitang.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String firstName;

    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String lastName;

    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String email;


    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    @JsonFormat(pattern="dd/MM/yyyy")
    private String birthday;

    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String login;

    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String password;

    @NotBlank(message = "Missing fields")
    @NotNull(message = "Invalid fields")
    private String phone;

    private List<CarroDTO> cars;
}
