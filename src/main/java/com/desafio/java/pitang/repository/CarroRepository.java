package com.desafio.java.pitang.repository;

import com.desafio.java.pitang.model.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    boolean existsByLicensePlate(String licensePlate);

//    @Query(value = "SELECT * FROM tb_carro WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Carro> findAllByUsuarioId(Long idUsuario);

//    @Query(value = "SELECT * FROM tb_carro WHERE id_usuario = :idUsuario and id = :id", nativeQuery = true)
    Optional<Carro> findByIdAndUsuarioId(Long id, Long idUsuario);

    boolean existsByIdAndUsuarioId(Long id, Long usuarioId);
}
