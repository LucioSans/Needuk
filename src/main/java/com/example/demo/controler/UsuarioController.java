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
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/")
    @CrossOrigin(origins = "*")
    public Usuario create(@RequestBody Usuario user){return usuarioService.save(user);}

    @GetMapping("/usuarios/{id}")
    @CrossOrigin(origins = "*")
    public Usuario getById(@PathVariable Long Id){return usuarioService.getById(Id);}

    @GetMapping("/usuarios")
    @CrossOrigin(origins = "*")
    public List<Usuario> getAll(){return usuarioService.getAll();}

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<LoginRequestDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        boolean autenticado = usuarioService.autenticar(loginRequestDTO.getEmail(), String.valueOf(loginRequestDTO.getSenha()));
        if (autenticado) {
            // Agora, você INSTANCIA um objeto LoginResponseDTO para o sucesso
            LoginRequestDTO autheticate = new LoginRequestDTO("Login realizado com sucesso", true);
            // E o Spring (Jackson) o converte automaticamente para o JSON acima
            return ResponseEntity.ok(autheticate);
        } else {
            // Aqui, você INSTANCIA um objeto LoginResponseDTO para a falha
            LoginRequestDTO authenticate = new LoginRequestDTO("E-mail ou Senha inválidos.", false);
            // E o Spring o converte automaticamente para o JSON de falha
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticate);
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updatedUser = usuarioService.updateUsuario(id, usuario);

        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
