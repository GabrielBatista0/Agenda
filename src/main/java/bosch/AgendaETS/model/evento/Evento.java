package bosch.AgendaETS.model.evento;


import bosch.AgendaETS.model.turma.DadosAtualizacaoTurma;
import bosch.AgendaETS.model.turma.Turma;
import bosch.AgendaETS.model.usuario.Usuario;
import bosch.AgendaETS.model.usuario.UsuarioRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name="Evento")
@Table(name="evento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private LocalDateTime data_inicio;
    private LocalDateTime data_fim;
    private String local;
    private String instrutor;
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma")
    private Turma id_turma;




    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", data_inicio=" + data_inicio +
                ", data_fim=" + data_fim +
                ", local='" + local + '\'' +
                ", instrutor='" + instrutor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", id_turma=" + id_turma +
                '}';
    }


//    public Evento (DadosCadastroEvento dadosCadastroEvento){
//        this.titulo = dadosCadastroEvento.titulo();
//        this.data_inicio = dadosCadastroEvento.data_inicio();
//        this.data_fim = dadosCadastroEvento.data_fim();
//        this.local = dadosCadastroEvento.local();
//        this.instrutor = dadosCadastroEvento.instrutor();
//        this.descricao = dadosCadastroEvento.descricao();
//        this.id_turma = dadosCadastroEvento.id_turma();
//        this.id_usuario = dadosCadastroEvento.id_usuario();
//    }
    public void atualizarInfo(DadosAtualizacaoEvento dadosAtualizacaoEvento, Turma turma) {
        if (dadosAtualizacaoEvento.titulo() != null ){
            this.titulo = dadosAtualizacaoEvento.titulo();
        }
        if (dadosAtualizacaoEvento.data_inicio() != null){
            this.data_inicio = dadosAtualizacaoEvento.data_inicio();
        }if (dadosAtualizacaoEvento.data_fim() != null){
            this.data_fim = dadosAtualizacaoEvento.data_fim();
        }if (dadosAtualizacaoEvento.local() != null){
            this.local = dadosAtualizacaoEvento.local();
        }if (dadosAtualizacaoEvento.instrutor() != null){
            this.instrutor = dadosAtualizacaoEvento.instrutor();
        }if (dadosAtualizacaoEvento.descricao() != null){
            this.descricao = dadosAtualizacaoEvento.descricao();
        }if (dadosAtualizacaoEvento.id_turma() != null){
            this.id_turma = turma;
        }

    }



}
