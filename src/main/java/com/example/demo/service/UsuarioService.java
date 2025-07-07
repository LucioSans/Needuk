package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    //Listar
    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }
    //Procurar por Id
    public Usuario getById(Long Id){
        return usuarioRepository.findById(Id).orElseThrow();
    }
    //Criar
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    //Deletar
    public void delete(Long Id){
        usuarioRepository.deleteById(Id);
    }
    //Login
    public boolean autenticar(String email, String senha){
        return usuarioRepository.findByEmailAndSenha(email, senha).isPresent();
    }
    //Atualizar usuário
    public Optional<Usuario> updateUsuario(Long id, Usuario updatedUserDetails) {
        Optional<Usuario> existingUserOptional = usuarioRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            Usuario existingUser = existingUserOptional.get();


            if (updatedUserDetails.getNome() != null && !updatedUserDetails.getNome().isEmpty()) {
                existingUser.setNome(updatedUserDetails.getNome());
            }
            if (updatedUserDetails.getEmail() != null && !updatedUserDetails.getEmail().isEmpty()) {
                existingUser.setEmail(updatedUserDetails.getEmail());
            }

            return Optional.of(usuarioRepository.save(existingUser));
        } else {
            return Optional.empty();
        }
    }
}
