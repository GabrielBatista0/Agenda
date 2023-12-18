package bosch.AgendaETS.infra.seguranca;

import bosch.AgendaETS.model.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("{api.security.token.secret}")
    private String secret;

    public String GerarToken (Usuario usuario){
        try {
            var encript = Algorithm.HMAC256(secret);
            return JWT.create().withSubject(usuario.getUsuario())
                    .withIssuer("API")
                    .withExpiresAt(dataExpiracao())
                    .sign(encript);
        }
        catch (JWTCreationException exception){
            throw new RuntimeException("Erro de criação do TOKEN");
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusWeeks(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token){
        try {
            var encript = Algorithm.HMAC256(secret);
            return JWT.require(encript)
                    .withIssuer("API")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTCreationException e){
            throw new RuntimeException("TOKEN INVÁLIDO");
        }
    }
}
