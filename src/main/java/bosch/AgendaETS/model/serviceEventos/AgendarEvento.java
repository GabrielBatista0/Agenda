package bosch.AgendaETS.model.serviceEventos;

import bosch.AgendaETS.model.evento.DadosCadastroEvento;
import bosch.AgendaETS.model.evento.DadosEvento;
import bosch.AgendaETS.model.evento.Evento;
import bosch.AgendaETS.model.evento.EventoRepository;
import bosch.AgendaETS.model.turma.TurmaRepository;
import bosch.AgendaETS.model.usuario.Usuario;
import bosch.AgendaETS.model.usuario.UsuarioRepository;
import lombok.Cleanup;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AgendarEvento {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public DadosEvento agendarEvento (DadosCadastroEvento dadosCadastroEvento){

        if (!turmaRepository.existsById(dadosCadastroEvento.id_turma())){
            throw new RuntimeException("ID da turma não existe");
        }

        var turma = turmaRepository.findById(dadosCadastroEvento.id_turma()).get();
        var evento = new Evento(0,dadosCadastroEvento.titulo(),dadosCadastroEvento.data_inicio(),dadosCadastroEvento.data_fim(),
                dadosCadastroEvento.local(), dadosCadastroEvento.instrutor(), dadosCadastroEvento.descricao(),turma);
        eventoRepository.save(evento);
        return new DadosEvento(evento);
    }

    public List<Evento> agendarEventoUpload (String caminhoArquivo) throws IOException {
        List<Evento> listaDeEventos =new ArrayList<>();
        var eventos  = lerArquivo(caminhoArquivo);
        try {

        eventos.forEach(DadosCadEvento ->{
            var turma = turmaRepository.findById(DadosCadEvento.id_turma()).get();
            var evento = new Evento(
                    0,
                    DadosCadEvento.titulo(),
                    DadosCadEvento.data_inicio(),
                    DadosCadEvento.data_fim(),
                    DadosCadEvento.local(),
                    DadosCadEvento.instrutor(),
                    DadosCadEvento.descricao(),
                    turma
            );
            eventoRepository.save(evento);
            listaDeEventos.add(evento);
        });
        }
        catch (Exception e){
            System.out.println(e);
        }
        return listaDeEventos;

    }

    public List<DadosCadastroEvento> lerArquivo(String caminho) throws IOException {

        List<DadosCadastroEvento> dadosCadastroEventos = new ArrayList<>();

        @Cleanup FileInputStream file = new FileInputStream(caminho);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        List<Row> rows = (List<Row>) toList(sheet.iterator());
        rows.remove(0);

        rows.forEach(row -> {
            List<Cell> cells = (List<Cell>) toList(row.cellIterator());
            DadosCadastroEvento DadosCadastroEvento = new DadosCadastroEvento(
                    cells.get(0).getStringCellValue(),
                    cells.get(1).getLocalDateTimeCellValue(),
                    cells.get(2).getLocalDateTimeCellValue(),
                    cells.get(3).getStringCellValue(),
                    cells.get(4).getStringCellValue(),
                    cells.get(5).getStringCellValue(),
                    buscarTurma(cells.get(6).getStringCellValue())
            );
            dadosCadastroEventos.add(DadosCadastroEvento);
        });
        apagarFile(caminho);
        return dadosCadastroEventos;

    }

    public int buscarTurma(String stringCellValue) {
        try {
            var turma = turmaRepository.findByNome(stringCellValue);
            System.out.println(turma);
            return turma.getId();

        } catch (Exception e) {
            System.out.println(e);
        }

        return 0;
    }

    private List<?> toList(Iterator<?> iterator) {
        return IteratorUtils.toList(iterator);
    }
    private void apagarFile (String caminho){
        try {
            File file = new File(caminho);
            System.out.println("APAGOU++++++_----_++_+_+");
            file.delete();
        }
        catch (Exception e){
            throw new RuntimeException("Não foi possivel apagar o arquivo após a leitura");
        }
    }

    public void imprimir(List<DadosCadastroEvento> lista) {
        lista.forEach(System.out::println);
    }
}
