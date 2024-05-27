package com.desafio.java.pitang.controller;

import com.desafio.java.pitang.model.dto.CarroDTO;
import com.desafio.java.pitang.model.entity.Usuario;
import com.desafio.java.pitang.service.CarroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("cars")
@AllArgsConstructor
public class CarroController {
    
    private final CarroService carroService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarroDTO> listarCarros(@AuthenticationPrincipal Usuario usuario) {
        return carroService.listarCarros(usuario);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarroDTO buscarCarro(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        return carroService.buscarCarro(id, usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarroDTO cadastrarCarro(@RequestBody  CarroDTO carroDTO, @AuthenticationPrincipal Usuario usuario) {
        return carroService.cadastrarCarros(carroDTO, usuario);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CarroDTO editarCarro(@PathVariable Long id,
                                  @RequestBody  CarroDTO carroDTO,
                                  @AuthenticationPrincipal Usuario usuario) {
        return carroService.editarCarros(carroDTO, id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCarro(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        carroService.removerCarro(id, usuario);
    }

}
