package com.techstore.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// =====================================================================
// ADAPTAR: Esta es tu TABLA MAESTRA
//
// Ejemplo — si te dan "Libros":
//   1. Renombra el archivo a Libro.java
//   2. Cambia "Producto" por "Libro" en toda la clase
//   3. Cambia @Table(name = "PRODUCTOS") por @Table(name = "LIBROS")
//   4. Cambia los campos de negocio (ver seccion abajo)
//
// Mantén SIEMPRE sin cambiar:
//   - estado, createdAt, updatedAt, deletedAt, restoredAt
// =====================================================================
@Data
@Entity
@Table(name = "PRODUCTOS")          // ADAPTAR: "LIBROS", "EQUIPOS", etc.
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")   // ADAPTAR: "id_libro", "id_equipo", etc.
    private Integer idProducto;     // ADAPTAR: idLibro, idEquipo, etc.

    // ---- ADAPTAR: Campos de negocio segun tu caso ----
    // Ejemplo Libreria:
    //   nombre      -> titulo
    //   descripcion -> autor
    //   categoria   -> genero
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;          // ADAPTAR: titulo, marca, codigo, etc.

    @Column(name = "descripcion", length = 255)
    private String descripcion;     // ADAPTAR: autor, modelo, detalle, etc.

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;       // ADAPTAR: genero, tipo, linea, etc.

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;      // Este casi siempre queda igual

    @Column(name = "stock", nullable = false)
    private Integer stock;          // Este casi siempre queda igual

    // ---- NO CAMBIAR: baja logica ----
    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    // ---- NO CAMBIAR: 4 campos de auditoria obligatorios ----
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;
}
