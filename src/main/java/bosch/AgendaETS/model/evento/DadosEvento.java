package bosch.AgendaETS.model.evento;

import bosch.AgendaETS.model.turma.Turma;
import bosch.AgendaETS.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

public record DadosEvento(
        int id,
        String titulo,
        LocalDateTime data_inicio,
        LocalDateTime data_fim,
        String local,
        String instrutor,
        String descricao,
        Integer id_turma
) {

    public DadosEvento(Evento evento){
        this(evento.getId(),
                evento.getTitulo(),
                evento.getData_inicio(),
                evento.getData_fim(),
                evento.getLocal(),
                evento.getInstrutor(),
                evento.getDescricao(),
                evento.getId_turma().getId());

    }

}
