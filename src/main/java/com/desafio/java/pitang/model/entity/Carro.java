package com.desafio.java.pitang.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "tb_carro")
@AllArgsConstructor
@NoArgsConstructor
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "car_year")
    private int year;
    @Column(name = "license_plate", unique = true)
    private String licensePlate;
    private String model;
    private String color;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
