package bosch.AgendaETS.model.evento;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAtualizacaoEvento (
        @NotNull int id,
        String titulo,
        LocalDateTime data_inicio,
        LocalDateTime data_fim,
        String local,
        String instrutor,
        String descricao,
        Integer id_turma
){
}
