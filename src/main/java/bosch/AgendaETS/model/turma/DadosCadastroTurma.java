package bosch.AgendaETS.model.turma;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTurma(
        @NotBlank String nome,
        @NotBlank String turno) {
}
