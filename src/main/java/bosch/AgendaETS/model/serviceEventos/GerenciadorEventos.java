//package bosch.AgendaETS.model.serviceEventos;
//
//import bosch.AgendaETS.model.evento.DadosCadastroEvento;
//import bosch.AgendaETS.model.evento.DadosEvento;
//import bosch.AgendaETS.model.evento.Evento;
//import bosch.AgendaETS.model.turma.Turma;
//import bosch.AgendaETS.model.turma.TurmaRepository;
//import lombok.Cleanup;
//import org.apache.commons.collections4.IteratorUtils;
//import org.apache.poi.ss.formula.functions.Rows;
//import org.apache.poi.ss.formula.functions.T;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//@Component
//public class GerenciadorEventos {
//    @Autowired
//    private TurmaRepository turmaRepository;
//
//
//    public List<DadosCadastroEvento> lerArquivo(String caminho) throws IOException {
//
//        List<DadosCadastroEvento> dadosCadastroEventos = new ArrayList<>();
//
//        @Cleanup FileInputStream file = new FileInputStream(caminho);
//        Workbook workbook = new XSSFWorkbook(file);
//
//        Sheet sheet = workbook.getSheetAt(0);
//
//        List<Row> rows = (List<Row>) toList(sheet.iterator());
//        rows.remove(0);
//
//        rows.forEach(row -> {
//            List<Cell> cells = (List<Cell>) toList(row.cellIterator());
//            DadosCadastroEvento DadosCadastroEvento = new DadosCadastroEvento(
//                    cells.get(0).getStringCellValue(),
//                    cells.get(1).getLocalDateTimeCellValue(),
//                    cells.get(2).getLocalDateTimeCellValue(),
//                    cells.get(3).getStringCellValue(),
//                    cells.get(4).getStringCellValue(),
//                    cells.get(5).getStringCellValue(),
//                    buscarTurma(cells.get(6).getStringCellValue()),
//                    1
//            );
//            dadosCadastroEventos.add(DadosCadastroEvento);
//        });
//        System.out.println("Passou aq 2");
//
//        return dadosCadastroEventos;
//
//    }
//
//    public int buscarTurma(String stringCellValue) {
//        try {
//            var x = turmaRepository.findById(2);
////            var turma = turmaRepository.findByNome(stringCellValue);
//
//            System.out.println(x);
//            return 2;
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return 0;
//    }
//
//    private List<?> toList(Iterator<?> iterator) {
//        return IteratorUtils.toList(iterator);
//    }
//
//    public void imprimir(List<DadosCadastroEvento> lista) {
//        System.out.println("aq");
//        lista.forEach(System.out::println);
//    }
//}
