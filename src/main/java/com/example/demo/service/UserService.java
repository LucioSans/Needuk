package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //Listar
    public List<User> getAll(){
        return userRepository.findAll();
    }
    //Procurar por Id
    public User getById(Long Id){
        return userRepository.findById(Id).orElseThrow();
    }
    //Criar
    public User save(User user){
        return userRepository.save(user);
    }
    //Deletar
    public void delete(Long Id){
        userRepository.deleteById(Id);
    }
    //Login
    public boolean autenticar(String email, String senha){
        return userRepository.findByEmailAndSenha(email, senha).isPresent();
    }
    //Atualizar usuário
    public Optional<User> updateUser(Long id, User updatedUserDetails) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();


            if (updatedUserDetails.getNome() != null && !updatedUserDetails.getNome().isEmpty()) {
                existingUser.setNome(updatedUserDetails.getNome());
            }
            if (updatedUserDetails.getEmail() != null && !updatedUserDetails.getEmail().isEmpty()) {
                existingUser.setEmail(updatedUserDetails.getEmail());
            }

            return Optional.of(userRepository.save(existingUser));
        } else {
            return Optional.empty();
        }
    }
}
