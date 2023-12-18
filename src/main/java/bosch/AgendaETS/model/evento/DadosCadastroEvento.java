package bosch.AgendaETS.model.evento;

import bosch.AgendaETS.model.turma.Turma;
import bosch.AgendaETS.model.usuario.Usuario;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;
import java.util.Date;

public record DadosCadastroEvento(@NotBlank String titulo,
                                  @NotNull LocalDateTime data_inicio,
                                  @NotNull @Future LocalDateTime data_fim,
                                  @NotBlank String local,
                                  @NotBlank String instrutor,
                                  @NotBlank String descricao,
                                  @NotNull Integer id_turma
                                  ) {
    public DadosCadastroEvento(@NotBlank String titulo, @NotNull LocalDateTime data_inicio, @NotNull @Future LocalDateTime data_fim, @NotBlank String local,@NotBlank String instrutor, @NotBlank String descricao, @NotNull Integer id_turma) {
        this.titulo = titulo;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.local = local;
        this.instrutor = instrutor;
        this.descricao = descricao;
        this.id_turma = id_turma;
    }
}
