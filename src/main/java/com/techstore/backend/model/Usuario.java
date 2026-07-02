package com.techstore.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "[user]")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer idUsuario;

    @Column(name = "name", nullable = false, length = 100)
    private String nombre;

    @Column(name = "[user]", nullable = false, unique = true, length = 100)
    private String usuario;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String rol;

    @Column(name = "state", nullable = false)
    private Boolean estado = true;
}
