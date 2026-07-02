package com.techstore.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "enrollment")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer idVenta;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Producto estudiante;

    @ManyToOne
    @JoinColumn(name = "career_id", nullable = false)
    private Career carrera;

    @Column(name = "venue_name", nullable = false, length = 200)
    private String venueName;

    @Column(name = "promoter", length = 200)
    private String promoter;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

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
