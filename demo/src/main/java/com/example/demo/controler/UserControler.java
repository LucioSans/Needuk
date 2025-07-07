package com.example.demo.controler;
import com.example.demo.model.dto.LoginRequestDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserControler {

    private final UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @CrossOrigin(origins = "*")
    public User create(@RequestBody User user){return userService.save(user);}

    @GetMapping("/usuarios/{id}")
    @CrossOrigin(origins = "*")
    public User getById(@PathVariable Long Id){return userService.getById(Id);}

    @GetMapping("/usuarios")
    @CrossOrigin(origins = "*")
    public List<User> getAll(){return userService.getAll();}

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        boolean autenticate = userService.autenticar(loginRequestDTO.getEmail(), loginRequestDTO.getSenha());
        if (autenticate) {
            return ResponseEntity.ok("Login realizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou Senha inválidos.");
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> updatedUser = userService.updateUser(id, user);

        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
