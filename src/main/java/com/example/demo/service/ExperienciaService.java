package com.example.demo.service;

import com.example.demo.model.Experiencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ExperienciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExperienciaService {
    private final ExperienciaRepository experienciaRepository;
    private final UsuarioService usuarioService;

    public ExperienciaService(ExperienciaRepository experienciaRepository, UsuarioService usuarioService) {
        this.experienciaRepository = experienciaRepository;
        this.usuarioService = usuarioService;
    }

    public List<Experiencia> getAll(Long usuarioId) {
        return experienciaRepository.findByUser_Id(usuarioId);
    }

    public Experiencia save(Experiencia experiencia, Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + usuarioId));
        experiencia.setUser(usuario);
        return experienciaRepository.save(experiencia);
    }

    public void delete(Long id, Long usuarioId) {
        Experiencia experiencia = experienciaRepository.findByIdAndUser_Id(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Experiência não encontrada ou acesso negado."));
        experienciaRepository.deleteById(id);
    }

    public Experiencia findById(Long id, Long usuarioId) {
        return experienciaRepository.findByIdAndUser_Id(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Experiência não encontrada para o ID: " + id));
    }

    public Experiencia update(Long id, Experiencia experienciaAtualizada, Long usuarioId) {
        return experienciaRepository.findByIdAndUser_Id(id, usuarioId)
                .map(experienciaExistente -> {
                    experienciaExistente.setTitulo(experienciaAtualizada.getTitulo());
                    experienciaExistente.setTipo(experienciaAtualizada.getTipo());
                    experienciaExistente.setDataInicio(experienciaAtualizada.getDataInicio());
                    experienciaExistente.setDataFim(experienciaAtualizada.getDataFim());
                    experienciaExistente.setCargaHoraria(experienciaAtualizada.getCargaHoraria());
                    experienciaExistente.setHabilidades(experienciaAtualizada.getHabilidades());
                    experienciaExistente.setDescricao(experienciaAtualizada.getDescricao());
                    return experienciaRepository.save(experienciaExistente);
                }).orElseThrow(() -> new RuntimeException("Experiência não encontrada ou acesso negado."));
    }
}