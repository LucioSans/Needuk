package com.example.demo.controler;
import com.example.demo.model.Experiencia;
import com.example.demo.service.ExperienciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiencias")

public class ExperienciaController {

    private final ExperienciaService experienciaService;

    public ExperienciaController(ExperienciaService experienciaService) {
        this.experienciaService = experienciaService;
    }

    @PostMapping
    public ResponseEntity<Experiencia> create(@RequestBody Experiencia experiencia) {
        Experiencia savedExperiencia = experienciaService.save(experiencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExperiencia);
    }

    @GetMapping
    public List<Experiencia> getAll() {
        return experienciaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experiencia> findById(@PathVariable Long id) {
        Experiencia experiencia = experienciaService.findById(id);
        if (experiencia != null) {
            return ResponseEntity.ok(experiencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experiencia> update(@PathVariable Long id, @RequestBody Experiencia experiencia) {
        Experiencia updatedExperiencia = experienciaService.update(id, experiencia);
        if (updatedExperiencia != null) {
            return ResponseEntity.ok(updatedExperiencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        experienciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
