package com.techstore.backend.repository;

import com.techstore.backend.model.Producto;  // ADAPTAR: importa tu nueva entidad
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// =====================================================================
// ADAPTAR: Si renombraste Producto a Libro:
//   1. Renombra el archivo a LibroRepository.java
//   2. Cambia "Producto" por "Libro" en los 2 lugares marcados
//   3. Cambia "existsByNombreIgnoreCase" por el campo unico de tu entidad
//      Ejemplo: existsByTituloIgnoreCase, existsByCodigoIgnoreCase
// =====================================================================
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> { // ADAPTAR: <Libro, Integer>

    List<Producto> findByEstadoTrue();              // No cambiar

    List<Producto> findByEstado(Boolean estado);    // No cambiar

    // ADAPTAR: Cambia "Nombre" por el campo unico de tu entidad
    // Ejemplo: existsByTituloIgnoreCase, existsByCodigoIgnoreCase
    boolean existsByNombreIgnoreCase(String nombre);
}
