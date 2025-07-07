package com.example.demo.controler;
import com.example.demo.model.Experiencia;
import com.example.demo.service.ExperienciaService;
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
    public Experiencia create(@RequestBody Experiencia experiencia) {
        return experienciaService.save(experiencia);
    }

    @GetMapping
    public List<Experiencia> getAll() {
        return experienciaService.getAll();
    }

    @GetMapping("/find")
    public Experiencia findById(
        @RequestParam Long id
    ) {
        return experienciaService.findById(id);
    }

    @PutMapping("/{id}")
    public Experiencia update(@PathVariable Long Id, @RequestBody Experiencia experiencia) {
        return experienciaService.update(Id, experiencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long Id) {
        experienciaService.delete(Id);
        return ResponseEntity.noContent().build();
    }
}
