package bosch.AgendaETS.infra.exeptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratar404 (){return ResponseEntity.notFound().build();}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException e){
        var erro = e.getFieldError();
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarRegraDeNegocio(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> erroDeAcesso(TokenExpiredException e){
        return new ResponseEntity<String>("Unauthorized - Token JWT não encontrado",HttpStatus.UNAUTHORIZED);
    }

}
