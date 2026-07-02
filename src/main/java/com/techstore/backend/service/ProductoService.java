package com.techstore.backend.service;

import com.techstore.backend.model.Producto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

// ============================================================
// INTERFACE: ProductoService
// ADAPTAR: Si tu tabla maestra se llama diferente (ej. Proveedor,
//          Articulo, Equipo), renombra esta interface y su impl.
// ============================================================
public interface ProductoService {
    List<Producto> listar();
    List<Producto> listarPorEstado(Boolean estado);
    Optional<Producto> buscarPorId(Integer id);
    Producto guardar(Producto producto);
    boolean eliminar(Integer id);
    boolean restaurar(Integer id);

    // Export
    byte[] exportarExcel(Boolean estado) throws IOException;
    byte[] exportarPDF(Boolean estado) throws Exception;

    // Import
    int importarDesdeCSV(MultipartFile archivo) throws IOException;
    int importarDesdeExcel(MultipartFile archivo) throws IOException;
}
