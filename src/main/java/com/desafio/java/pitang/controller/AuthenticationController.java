package com.desafio.java.pitang.controller;

import com.desafio.java.pitang.exception.InvalidCredentialException;
import com.desafio.java.pitang.model.dto.AuthenticationDTO;
import com.desafio.java.pitang.model.dto.UsuarioDTO;
import com.desafio.java.pitang.model.dto.UsuarioLoginDTO;
import com.desafio.java.pitang.model.entity.Usuario;
import com.desafio.java.pitang.model.mapper.ConverterDTO;
import com.desafio.java.pitang.service.TokenService;
import com.desafio.java.pitang.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UsuarioService usuarioService;

    private ConverterDTO converter;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        if(auth.isAuthenticated()){

            usuarioService.atualizarUltimoLogin((Usuario) auth.getPrincipal());
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());
            return ResponseEntity.ok(new UsuarioLoginDTO(token,
                    (UsuarioDTO) converter.convertObject((Usuario) auth.getPrincipal(), UsuarioDTO.class)));
        }
        throw new InvalidCredentialException("Unauthorized");
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity me(@AuthenticationPrincipal Usuario usuario) {
        if (usuario != null) {
            return ResponseEntity.ok(converter.convertObject(usuario, UsuarioDTO.class));
        }
        throw new InvalidCredentialException("Unauthorized");
    }
}
