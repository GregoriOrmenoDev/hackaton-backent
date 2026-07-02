package com.techstore.backend.service.impl;

import com.techstore.backend.dto.VentaRequestDTO;
import com.techstore.backend.model.*;
import com.techstore.backend.repository.*;
import com.techstore.backend.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final CareerRepository careerRepository;

    @Override
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> buscarPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    @Override
    @Transactional
    public Venta registrarVenta(VentaRequestDTO request) {
        Producto estudiante = productoRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + request.getStudentId()));

        Career carrera = careerRepository.findById(request.getCareerId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada: " + request.getCareerId()));

        Venta matricula = new Venta();
        matricula.setEstudiante(estudiante);
        matricula.setCarrera(carrera);
        matricula.setVenueName(request.getVenueName());
        matricula.setPromoter(request.getPromoter());
        matricula.setTotal(request.getPrice());
        matricula.setEstado(true);
        matricula.setCreatedAt(LocalDateTime.now());

        return ventaRepository.save(matricula);
    }
}
