package com.techstore.backend.rest;

import com.techstore.backend.model.student;     // ADAPTAR: importa tu entidad
import com.techstore.backend.service.ProductoService; // ADAPTAR: importa tu service
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// =====================================================================
// ADAPTAR: Si renombraste la entidad:
//   1. Renombra el archivo a LibroController.java
//   2. Cambia @RequestMapping("/api/productos") por "/api/libros"
//   3. Cambia el @Tag name = "Productos" por "Libros"
//   4. Cambia "Producto" y "ProductoService" por tu entidad/service
// =====================================================================
@RestController
@RequestMapping("/api/productos")   // ADAPTAR: "/api/libros", "/api/equipos", etc.
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "CRUD + Export/Import tabla maestra") // ADAPTAR name
public class ProductoController {

    private final ProductoService productoService; // ADAPTAR: LibroService, etc.

    @Operation(summary = "Listar activos")
    @GetMapping
    public List<student> listar() {               // ADAPTAR: List<Libro>
        return productoService.listar();
    }

    @Operation(summary = "Listar por estado: ?estado=true/false")
    @GetMapping("/estado")
    public List<student> listarPorEstado(@RequestParam(required = false) Boolean estado) {
        return productoService.listarPorEstado(estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<student> buscarPorId(@PathVariable Integer id) { // ADAPTAR: <Libro>
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<student> crear(@RequestBody student producto) { // ADAPTAR: Libro
        return ResponseEntity.ok(productoService.guardar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<student> actualizar(@PathVariable Integer id, @RequestBody student producto) {
        return productoService.buscarPorId(id).map(e -> {
            producto.setIdProducto(id); // ADAPTAR: setIdLibro(id), etc.
            return ResponseEntity.ok(productoService.guardar(producto));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Baja logica (estado = false)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        return productoService.eliminar(id)
                ? ResponseEntity.ok("Registro desactivado")
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Restaurar (estado = true)")
    @PutMapping("/{id}/restaurar")
    public ResponseEntity<String> restaurar(@PathVariable Integer id) {
        return productoService.restaurar(id)
                ? ResponseEntity.ok("Registro restaurado")
                : ResponseEntity.notFound().build();
    }

    // ---- EXPORTAR (no cambiar los endpoints, solo el service) ----

    @Operation(summary = "Exportar Excel. ?status=true/false/sin-param=todos")
    @GetMapping("/exportar/excel")
    public ResponseEntity<byte[]> exportarExcel(@RequestParam(required = false) Boolean status) {
        try {
            byte[] data = productoService.exportarExcel(status);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datos.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Exportar PDF. ?status=true/false/sin-param=todos")
    @GetMapping("/exportar/pdf")
    public ResponseEntity<byte[]> exportarPDF(@RequestParam(required = false) Boolean status) {
        try {
            byte[] data = productoService.exportarPDF(status);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datos.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ---- IMPORTAR (no cambiar los endpoints, solo el service) ----

    @Operation(summary = "Importar CSV")
    @PostMapping("/importar/csv")
    public ResponseEntity<String> importarCSV(@RequestParam("archivo") MultipartFile archivo) {
        try {
            int n = productoService.importarDesdeCSV(archivo);
            return ResponseEntity.ok("Importados: " + n + " registros.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Importar Excel")
    @PostMapping("/importar/excel")
    public ResponseEntity<String> importarExcel(@RequestParam("archivo") MultipartFile archivo) {
        try {
            int n = productoService.importarDesdeExcel(archivo);
            return ResponseEntity.ok("Importados: " + n + " registros.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
