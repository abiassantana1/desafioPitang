package com.desafio.java.pitang.service;

import com.desafio.java.pitang.exception.BusinessException;
import com.desafio.java.pitang.exception.MultipleBusinessException;
import com.desafio.java.pitang.model.dto.CarroDTO;
import com.desafio.java.pitang.model.dto.UsuarioDTO;
import com.desafio.java.pitang.model.entity.Carro;
import com.desafio.java.pitang.model.entity.Usuario;
import com.desafio.java.pitang.model.mapper.ConverterDTO;
import com.desafio.java.pitang.repository.CarroRepository;
import com.desafio.java.pitang.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    CarroService carroService;

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    ConverterDTO converter;

    @Mock
    CarroRepository carroRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    List<Carro> carros;

    List<CarroDTO> carrosDTO;

    Usuario usuario;

    UsuarioDTO usuarioDTO;

    @Before
    public void setup() {

        MockitoAnnotations.openMocks(this);

        Carro carro1 = new Carro(1L,1,"1","1","1", usuario);
        Carro carro2 = new Carro(2L,2,"2","2","2", usuario);

        CarroDTO carro1DTO = new CarroDTO(1L,1,"1","1","1");
        CarroDTO carro2DTO = new CarroDTO(2L,2,"2","2","2");

        carros = List.of(carro1, carro2);
        carrosDTO = List.of(carro1DTO, carro2DTO);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCars(carros);
        usuario.setEmail("test@gmail.com");
        usuario.setLogin("login");
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setCars(carrosDTO);
        usuarioDTO.setEmail("test@gmail.com");
        usuarioDTO.setLogin("login");
    }

    @Test
    public void cadastrarUsuariosTest() {

        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(usuarioRepository.existsByLogin(any())).thenReturn(false);
        when(carroRepository.saveAll(any())).thenReturn(carros);
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(converter.convertObject(usuarioDTO, Usuario.class)).thenReturn(usuario);
        when(converter.convertObject(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);
        when(passwordEncoder.encode(any())).thenReturn("");


        UsuarioDTO result = usuarioService.cadastrarUsuarios(usuarioDTO);

        assertEquals(usuarioDTO, result);
    }

    @Test
    public void validarUsuarioEmailExiste() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(true);
        when(usuarioRepository.existsByLogin(any())).thenReturn(false);

        Exception exception = assertThrows(MultipleBusinessException.class, () -> {
            usuarioService.cadastrarUsuarios(usuarioDTO);
        });

        String expectedMessage = "Email already exists";
        String resultMessage = ((MultipleBusinessException) exception).getExceptions().get(0).getMessage();

        assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void validarUsuarioLoginExiste() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(usuarioRepository.existsByLogin(any())).thenReturn(true);

        Exception exception = assertThrows(MultipleBusinessException.class, () -> usuarioService.cadastrarUsuarios(usuarioDTO));

        String expectedMessage = "Login already exists";
        String resultMessage = ((MultipleBusinessException) exception).getExceptions().get(0).getMessage();

        assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void validarUsuarioEmailLoginExiste() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(true);
        when(usuarioRepository.existsByLogin(any())).thenReturn(true);

        Exception exception = assertThrows(MultipleBusinessException.class, () -> usuarioService.cadastrarUsuarios(usuarioDTO));

        String expectedMessage1 = "Email already exists";
        String expectedMessage2 = "Login already exists";
        List<BusinessException> result = ((MultipleBusinessException) exception).getExceptions();

        // Verifica o resultado
        assertAll( "Verifica o resultado listagem",
                () -> assertEquals(expectedMessage1, result.get(0).getMessage()),
                () -> assertEquals(expectedMessage2, result.get(1).getMessage())

        );
    }

    @Test
    public void removerUsuario() {

        when(usuarioRepository.existsById(any())).thenReturn(true);
        usuarioService.removerUsuario(1L);
    }

    @Test
    public void buscarUsuario() {
        Usuario usuario = new Usuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(this.usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        when(this.converter.convertObject(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.buscarUsuario(1L);

        assertEquals(usuarioDTO, result);
    }

}
