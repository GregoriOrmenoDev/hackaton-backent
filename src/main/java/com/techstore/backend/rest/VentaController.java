package com.techstore.backend.rest;

import com.techstore.backend.dto.VentaRequestDTO;
import com.techstore.backend.model.Venta;
import com.techstore.backend.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "Registro de ventas (transaccional)")
public class VentaController {

    private final VentaService ventaService;

    @Operation(summary = "Listar todas las ventas")
    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {
        return ventaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Body: { "idCliente": 1, "idUsuario": 1, "detalles": [{"idProducto": 1, "cantidad": 2}] }
    @Operation(summary = "Registrar nueva venta")
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody VentaRequestDTO request) {
        try {
            return ResponseEntity.ok(ventaService.registrarVenta(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
