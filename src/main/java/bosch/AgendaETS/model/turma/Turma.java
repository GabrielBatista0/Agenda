package bosch.AgendaETS.model.turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "turma")
@Entity(name ="Turma")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String turno;
    private String nome;
    private boolean ativo;


    public Turma(DadosCadastroTurma dadosCadastroTurma) {
        this.turno = dadosCadastroTurma.turno();
        this.nome = dadosCadastroTurma.nome();
        this.ativo = true;
    }

    public void atualizarInfo(DadosAtualizacaoTurma dadosAtualizacaoTurma) {
        if (dadosAtualizacaoTurma.nome() != null ){
            this.nome = dadosAtualizacaoTurma.nome();
        }
        if (dadosAtualizacaoTurma.turno() != null){
            this.turno = dadosAtualizacaoTurma.turno();
        }

    }

    public void desativar() {
        this.ativo = false;
    }
}
