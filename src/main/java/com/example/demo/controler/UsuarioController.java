package com.example.demo.controler;

import com.example.demo.model.dto.LoginRequestDTO;
import com.example.demo.model.dto.LoginResponseDTO;
import com.example.demo.model.Usuario;
import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.Jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario user){
        Usuario savedUser = usuarioService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public List<Usuario> getAll(){
        return usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        Usuario usuario = usuarioService.getById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<Usuario> usuarioAutenticadoOpt = usuarioService.autenticar(loginRequestDTO.email(), loginRequestDTO.senha());

        if (usuarioAutenticadoOpt.isPresent()) {
            LoginResponseDTO response = getLoginResponseDTO(usuarioAutenticadoOpt);
            return ResponseEntity.ok(response);
        } else {
            LoginResponseDTO response = new LoginResponseDTO("E-mail ou Senha inválidos.", false, null, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    private LoginResponseDTO getLoginResponseDTO(Optional<Usuario> usuarioAutenticadoOpt) {
        Usuario usuarioAutenticado = usuarioAutenticadoOpt.get();

        String token = jwtService.generateToken(String.valueOf(usuarioAutenticado.getUsuarioId()));

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuarioAutenticado.getUsuarioId(),
                usuarioAutenticado.getNome(),
                usuarioAutenticado.getEmail(),
                usuarioAutenticado.getTelefone()
        );

        return new LoginResponseDTO(
                "Login realizado com sucesso",
                true,
                token,
                usuarioDTO
        );
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