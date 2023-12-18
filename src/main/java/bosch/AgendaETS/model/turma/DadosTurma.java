package bosch.AgendaETS.model.turma;

public record DadosTurma (int id,
                          String nome,
                          String turno,
                          boolean ativo){
    public DadosTurma(Turma turma){
        this(turma.getId(), turma.getNome(), turma.getTurno(), turma.isAtivo());
    }
}
