package bosch.AgendaETS.model.turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTurma(@NotNull int id,
                                    String nome,
                                    String turno,
                                    boolean ativo) {


}
