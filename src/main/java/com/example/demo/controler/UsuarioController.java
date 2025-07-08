package com.example.demo.controler;
import com.example.demo.model.dto.LoginRequestDTO;
import com.example.demo.model.dto.LoginResponseDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/")
    public Usuario create(@RequestBody Usuario user){return usuarioService.save(user);}

    @GetMapping("/usuarios/{id}")
    public Usuario getById(@PathVariable Long Id){return usuarioService.getById(Id);}

    @GetMapping("/usuarios")
    public List<Usuario> getAll(){return usuarioService.getAll();}

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        boolean autenticado = usuarioService.autenticar(loginRequestDTO.email(), loginRequestDTO.senha());

        if (autenticado) {
            LoginResponseDTO response = new LoginResponseDTO("Login realizado com sucesso", true);
            return ResponseEntity.ok(response);
        } else {
            LoginResponseDTO response = new LoginResponseDTO("E-mail ou Senha inválidos.", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updatedUser = usuarioService.updateUsuario(id, usuario);

        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
