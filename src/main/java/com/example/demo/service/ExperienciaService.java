package com.example.demo.service;

import com.example.demo.model.Experiencia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ExperienciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienciaService {
    private final ExperienciaRepository experienciaRepository;
    private final UsuarioService usuarioService;

    public ExperienciaService(ExperienciaRepository experienciaRepository, UsuarioService usuarioService) {
        this.experienciaRepository = experienciaRepository;
        this.usuarioService = usuarioService;
    }

    public List<Experiencia> getAll(Long usuarioId) {
        return experienciaRepository.findByUserId(usuarioId);
    }

    public Experiencia save(Experiencia experiencia, Long usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + usuarioId));
        experiencia.setUser(usuario);

        // Validação: verifica se já existe uma experiência com o mesmo título
        Optional<Experiencia> experienciaComMesmoTitulo = experienciaRepository
            .findByUserIdAndTitulo(usuarioId, experiencia.getTitulo());

        if (experienciaComMesmoTitulo.isPresent()) {
            throw new IllegalArgumentException("Já existe uma experiência com este título para o usuário.");
        }

        return experienciaRepository.save(experiencia);
    }

    public void delete(Long id, Long usuarioId) {
        Experiencia experiencia = experienciaRepository.findByIdAndUserId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Experiência não encontrada ou acesso negado."));
        experienciaRepository.deleteById(id);
    }

    public Experiencia findById(Long id, Long usuarioId) {
        return experienciaRepository.findByIdAndUserId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Experiência não encontrada para o ID: " + id));
    }

    public Experiencia update(Long id, Experiencia experienciaAtualizada, Long usuarioId) {
        return experienciaRepository.findByIdAndUserId(id, usuarioId)
                .map(experienciaExistente -> {
                    // Adiciona a validação do título antes de atualizar
                    Optional<Experiencia> experienciaComMesmoTitulo = experienciaRepository
                        .findByUserIdAndTitulo(usuarioId, experienciaAtualizada.getTitulo());

                    if (experienciaComMesmoTitulo.isPresent() && !experienciaComMesmoTitulo.get().getId().equals(id)) {
                        throw new IllegalArgumentException("Já existe uma experiência com este título para o usuário.");
                    }

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
