package com.techstore.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VentaRequestDTO {
    private Integer studentId;
    private Integer careerId;
    private String venueName;
    private String promoter;
    private BigDecimal price;
}
