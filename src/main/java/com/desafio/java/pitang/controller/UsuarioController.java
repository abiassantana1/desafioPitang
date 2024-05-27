package com.desafio.java.pitang.controller;

import com.desafio.java.pitang.model.dto.UsuarioBasicoDTO;
import com.desafio.java.pitang.model.dto.UsuarioDTO;
import com.desafio.java.pitang.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioBasicoDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarUsuario(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.cadastrarUsuarios(usuarioDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO editarUsuario(@PathVariable Long id,
                                    @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editarUsuarios(usuarioDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
    }
}
