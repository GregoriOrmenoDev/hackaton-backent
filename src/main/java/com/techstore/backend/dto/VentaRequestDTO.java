package com.techstore.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class VentaRequestDTO {
    private Integer idCliente;
    private Integer idUsuario;
    private List<DetalleVentaDTO> detalles;
}
