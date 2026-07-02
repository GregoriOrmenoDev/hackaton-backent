package com.techstore.backend.service;

import com.techstore.backend.dto.VentaRequestDTO;
import com.techstore.backend.model.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<Venta> listar();
    Optional<Venta> buscarPorId(Integer id);
    Venta registrarVenta(VentaRequestDTO request);
}
