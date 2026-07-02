package com.techstore.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Student")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer idProducto;

    @Column(name = "name", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DNI", nullable = false, unique = true, length = 8)
    private String descripcion;

    @Column(name = "phone", nullable = false, length = 15)
    private String categoria;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "state", nullable = false)
    private Boolean estado = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;
}
