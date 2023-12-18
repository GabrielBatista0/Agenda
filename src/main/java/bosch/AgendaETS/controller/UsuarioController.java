package bosch.AgendaETS.controller;

import bosch.AgendaETS.model.usuario.DadosCadastroUsuario;
import bosch.AgendaETS.model.usuario.DadosUsuario;
import bosch.AgendaETS.model.usuario.Usuario;
import bosch.AgendaETS.model.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*") //Consumir via codigo
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastro de usuário - Aberto ", description = "Cadastra um novo usuário",
            tags = {"Usuário"})
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        var usuario = new Usuario(dadosCadastroUsuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repository.save(usuario);
        var uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosUsuario(usuario));
    }

}
