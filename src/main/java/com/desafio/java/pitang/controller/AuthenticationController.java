package com.desafio.java.pitang.controller;

import com.desafio.java.pitang.model.dto.AuthenticationDTO;
import com.desafio.java.pitang.model.dto.UsuarioDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UsuarioService usuarioService;

    private ConverterDTO converter;

    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        if(auth.isAuthenticated()){
            usuarioService.atualizarUltimoLogin((Usuario) auth.getPrincipal());
        }
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity me(@AuthenticationPrincipal Usuario usuario) {
        if (usuario != null) {
            return ResponseEntity.ok(converter.convertObject(usuario, UsuarioDTO.class));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No user is currently logged in");
        }
    }
}
