package com.desafio.java.pitang.service;

import com.desafio.java.pitang.exception.BusinessException;
import com.desafio.java.pitang.exception.MultipleBusinessException;
import com.desafio.java.pitang.exception.SourceNotFoundException;
import com.desafio.java.pitang.model.dto.UsuarioBasicoDTO;
import com.desafio.java.pitang.model.dto.UsuarioDTO;
import com.desafio.java.pitang.model.entity.Carro;
import com.desafio.java.pitang.model.entity.Usuario;
import com.desafio.java.pitang.model.mapper.ConverterDTO;
import com.desafio.java.pitang.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final CarroService carroService;

    private ConverterDTO converter;

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public List<UsuarioBasicoDTO> listarUsuarios() {
        return this.converter.converterListObjects(this.usuarioRepository.findAll(), UsuarioBasicoDTO.class);
    }

    @Transactional(rollbackOn = Throwable.class)
    public UsuarioDTO cadastrarUsuarios(UsuarioDTO usuarioDTO) {
        this.validarUsuario(usuarioDTO);
        List<Carro> carros = this.carroService.cadastrarListaCarros(usuarioDTO.getCars());
        Usuario usuario = (Usuario) this.converter.convertObject(usuarioDTO, Usuario.class);
        usuario.setCars(carros);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setCreatedAt(new Date());
        usuario = this.usuarioRepository.save(usuario);
        return (UsuarioDTO) this.converter.convertObject(usuario , UsuarioDTO.class);
    }

    @Transactional(rollbackOn = Throwable.class)
    public UsuarioDTO editarUsuarios(UsuarioDTO usuarioDTO, Long id) {
        this.validarUsuario(usuarioDTO);
        if(usuarioRepository.existsById(id)) {
            Usuario usuario = (Usuario) this.converter.convertObject(usuarioDTO, Usuario.class);
            usuario.setId(id);
            usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            return (UsuarioDTO) this.converter.convertObject(this.usuarioRepository.save(usuario) , UsuarioDTO.class);
        } else {
            throw new SourceNotFoundException("");
        }
    }

    public void atualizarUltimoLogin(Usuario usuario) {
        usuario.setLastLogin(new Date());
        this.usuarioRepository.save(usuario);
    }

    private void validarUsuario(UsuarioDTO usuario) {
        List<BusinessException> exceptions = new ArrayList<>();
        if(this.usuarioRepository.existsByEmail(usuario.getEmail())){
            exceptions.add(new BusinessException("Email already exists"));
        }
        if(this.usuarioRepository.existsByLogin(usuario.getLogin())){
            exceptions.add(new BusinessException("Login already exists"));
        }
        if (!exceptions.isEmpty()) {
            throw new MultipleBusinessException(exceptions);
        }
    }

    public void removerUsuario(Long id) {
        if(usuarioRepository.existsById(id)) {
            this.usuarioRepository.deleteById(id);
        } else {
            throw new SourceNotFoundException("");
        }
    }

    public UsuarioDTO buscarUsuario(Long id) {
        Usuario usuario = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhum carro encontrado com este id"));
        return (UsuarioDTO) this.converter.convertObject(usuario, UsuarioDTO.class);
    }
}
