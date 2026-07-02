package com.techstore.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// ============================================================
// ENTIDAD: Usuario  ->  tabla USUARIOS
// ADAPTAR: Si te dan otro caso con otra entidad de login, cambia
//          los campos aqui y en la tabla SQL.
// ============================================================
@Data
@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "usuario", nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    // Valores: ADMIN, VENDEDOR
    @Column(name = "rol", nullable = false, length = 20)
    private String rol;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
}
