package com.example.demo.repository;

import com.example.demo.model.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienciaRepository  extends JpaRepository<Experiencia, Long> {

    List<Experiencia> findByUserId(Long usuarioId);
    Optional<Experiencia> findByIdAndUserId(Long id, Long usuarioId);
}