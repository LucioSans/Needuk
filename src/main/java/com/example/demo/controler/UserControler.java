package com.example.demo.controler;
import com.example.demo.model.dto.LoginRequestDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserControler {

    private final UsuarioService usuarioService;

    public UserControler(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/")
    public Usuario create(@RequestBody Usuario user){return usuarioService.save(user);}

    @GetMapping("/usuarios/{id}")
    public Usuario getById(@PathVariable Long Id){return usuarioService.getById(Id);}

    @GetMapping("/usuarios")
    public List<Usuario> getAll(){return usuarioService.getAll();}

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        boolean autenticate = usuarioService.autenticar(loginRequestDTO.getEmail(), loginRequestDTO.getSenha());
        if (autenticate) {
            return ResponseEntity.ok("Login realizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou Senha inválidos.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario user) {
        Optional<Usuario> updatedUser = usuarioService.updateUser(id, user);

        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}