package com.desafio.java.pitang.repository;

import com.desafio.java.pitang.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    UserDetails findByLogin(String login);
}
