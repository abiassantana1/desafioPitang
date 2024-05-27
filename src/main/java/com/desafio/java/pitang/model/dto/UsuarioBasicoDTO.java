package com.desafio.java.pitang.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioBasicoDTO {

    private Long id;

    @NotNull(message = "Missing fields")
    private String firstName;


    @NotNull(message = "Missing fields")
    private String lastName;


    @NotNull(message = "Missing fields")
    private String email;


    @NotNull(message = "Missing fields")
//    @JsonFormat(pattern="dd/MM/yyyy")
    private String birthday;


    @NotNull(message = "Missing fields")
    private String login;

    @NotNull(message = "Missing fields")
    private String phone;

    private List<CarroDTO> cars;

//    @JsonFormat(pattern="dd/MM/yyyy")
    private Date createdAt;

    private Date lastLogin;
}
