package com.example.demo.controler;

import com.example.demo.model.Experiencia;
import com.example.demo.service.ExperienciaService;
import com.example.demo.service.Jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/experiencias")
public class ExperienciaController {

    private final ExperienciaService experienciaService;
    private final JwtService jwtService;

    public ExperienciaController(ExperienciaService experienciaService, JwtService jwtService) {
        this.experienciaService = experienciaService;
        this.jwtService = jwtService;
    }

    private Long getUsuarioIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtService.extractUserId(token);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token de autenticação ausente ou inválido.");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Experiencia experiencia, HttpServletRequest request) {
        Long usuarioId = getUsuarioIdFromRequest(request);
        try {
            Experiencia savedExperiencia = experienciaService.save(experiencia, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExperiencia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Experiencia> getAll(HttpServletRequest request) {
        Long usuarioId = getUsuarioIdFromRequest(request);
        return experienciaService.getAll(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experiencia> findById(@PathVariable Long id, HttpServletRequest request) {
        Long usuarioId = getUsuarioIdFromRequest(request);
        Experiencia experiencia = experienciaService.findById(id, usuarioId);
        return ResponseEntity.ok(experiencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Experiencia experiencia, HttpServletRequest request) {
        Long usuarioId = getUsuarioIdFromRequest(request);
        try {
            Experiencia updatedExperiencia = experienciaService.update(id, experiencia, usuarioId);
            return ResponseEntity.ok(updatedExperiencia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long usuarioId = getUsuarioIdFromRequest(request);
        experienciaService.delete(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
