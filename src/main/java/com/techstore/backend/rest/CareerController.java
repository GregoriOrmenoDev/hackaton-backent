package com.techstore.backend.rest;

import com.techstore.backend.model.Career;
import com.techstore.backend.repository.CareerRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Carreras", description = "Carreras disponibles")
public class CareerController {

    private final CareerRepository careerRepository;

    @GetMapping
    public List<Career> listar() {
        return careerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Career> buscarPorId(@PathVariable Integer id) {
        return careerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Career crear(@RequestBody Career career) {
        return careerRepository.save(career);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Career> actualizar(@PathVariable Integer id, @RequestBody Career career) {
        return careerRepository.findById(id).map(e -> {
            career.setIdCareer(id);
            return ResponseEntity.ok(careerRepository.save(career));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        careerRepository.deleteById(id);
        return ResponseEntity.ok("Carrera eliminada");
    }
}
