package com.example.demo.service;

import com.example.demo.model.Experiencia;
import com.example.demo.repository.ExperienciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ExperienciaService {
    private final ExperienciaRepository experienciaRepository;

    public ExperienciaService(ExperienciaRepository experienciaRepository) {
        this.experienciaRepository = experienciaRepository;
    }
    //Listar
    public List<Experiencia> getAll(){
        return experienciaRepository.findAll();
    }
    //Criar
    public Experiencia save(Experiencia experiencia) {
        return experienciaRepository.save(experiencia);
    }
    //Deletar
    public void delete(Long Id){
        experienciaRepository.deleteById(Id);
    }
    //findById
    public Experiencia findById(Long id) {
        return experienciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));
    }

    //Atualiza a experiencia
    public Experiencia update(Long Id, Experiencia experiencia){
        if(experienciaRepository.existsById(Id)){
        Experiencia updateExperiencia = experienciaRepository.save(experiencia);
        return updateExperiencia;
        }
        return null;
    }

}
