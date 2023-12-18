package bosch.AgendaETS.model.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public record DadosCadastroUsuario (
        @NotBlank String usuario,
        @NotNull String senha
)
{


}
