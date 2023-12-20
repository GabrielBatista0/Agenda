package bosch.AgendaETS.controller;

import bosch.AgendaETS.infra.seguranca.DadosTokenJWT;
import bosch.AgendaETS.infra.seguranca.TokenService;
import bosch.AgendaETS.model.usuario.DadosAutenticacao;
import bosch.AgendaETS.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin("*") //Consumir via codigo
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    @Operation(summary = "login de usuário", description = "Retorna o token de acesso do user",
            tags = {"Login"})
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.usuario(), dadosAutenticacao.senha());
        var autenticacao = manager.authenticate(token);
        var tokenJWT = tokenService.GerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/validarToken")
    @Operation(summary = "Verificação de token", description = "Retorna se o token de acesso do user está valido",
            tags = {"verificar"})
    public ResponseEntity verificarToken(@RequestBody @Valid DadosTokenJWT dadosTokenJWT){
        try {
            var token = dadosTokenJWT.tokenJWT();
            tokenService.getSubject(token);
            return ResponseEntity.ok("Token Válido");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Inválido");
        }
    }
}
