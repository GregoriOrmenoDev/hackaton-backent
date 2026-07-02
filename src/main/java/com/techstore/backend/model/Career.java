package com.techstore.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "career")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Integer idCareer;

    @Column(name = "career", nullable = false, length = 200)
    private String career;

    @Column(name = "cycles", length = 1)
    private String cycles;

    @Column(name = "investment", precision = 10, scale = 2)
    private BigDecimal investment;
}
