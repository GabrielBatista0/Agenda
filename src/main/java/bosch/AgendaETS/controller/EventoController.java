package bosch.AgendaETS.controller;

import bosch.AgendaETS.model.evento.*;
import bosch.AgendaETS.model.serviceEventos.AgendarEvento;
import bosch.AgendaETS.model.turma.DadosAtualizacaoTurma;
import bosch.AgendaETS.model.turma.DadosTurma;
import bosch.AgendaETS.model.turma.Turma;
import bosch.AgendaETS.model.turma.TurmaRepository;
import bosch.AgendaETS.model.usuario.Usuario;
import bosch.AgendaETS.model.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/eventos")
@CrossOrigin("*") //Consumir via codigo
public class EventoController {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AgendarEvento agendarEvento;
    private final String pathArquivos;

    public EventoController(@Value("${app.path.arquivos}") String pathArquivos) {

        this.pathArquivos = pathArquivos;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um evento sem utilizar um arquivo", description = "Cadastra um evento sem utilizar um excel para passa as informações para o banco de dados",
    tags = {"Evento"})
    public ResponseEntity cadastrarEvento (@RequestBody @Valid DadosCadastroEvento dadosCadastroEvento){
       var eventoCadastrado = agendarEvento.agendarEvento(dadosCadastroEvento);
        return ResponseEntity.ok(eventoCadastrado);
    }

    @GetMapping //Esse parameter serve para tirar a obrigação de ter um pageble na req pelo swagger
    @Operation(summary = "Retorna todos os eventos",tags = {"Evento"})
    public ResponseEntity <Page<DadosEvento>> listarEventos(@ParameterObject Pageable pageable){
        var lista = repository.findAll(pageable).map(DadosEvento::new);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}") //Esse parameter serve para tirar a obrigação de ter um pageble na req pelo swagger
    @Operation(summary = "Retorna todos os eventos",tags = {"Evento"})
    public ResponseEntity listarEventoEspecifico(@PathVariable int id){
        var evento = repository.findById(id).get();
        return ResponseEntity.ok(new DadosEvento(evento));
    }



    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cadastra um evento utilizando um arquivo", description = "Cadastra um evento utilizando um excel para passa as informações para o banco de dados",
            tags = {"Evento"})
    public ResponseEntity<List<DadosEvento>> salvarArquivo(@RequestParam("file") MultipartFile file){
        List<DadosEvento> listaDadosEvento = new ArrayList<>();
        var caminho = pathArquivos+ UUID.randomUUID()+"."+pegarExtensao(file.getOriginalFilename());
        System.out.println("Novo nome/caminho "+caminho);

        try {
            Files.copy(file.getInputStream(), Path.of(caminho), StandardCopyOption.REPLACE_EXISTING);
            var eventos = agendarEvento.agendarEventoUpload(caminho);
            eventos.forEach(evento -> listaDadosEvento.add(new DadosEvento(evento)));

            return new ResponseEntity<List<DadosEvento>>(listaDadosEvento,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza um evento", description = "Atualiza um evento com os parametros necessários",
            tags = {"Evento"})
    public ResponseEntity atualizarEvento (@RequestBody @Valid DadosAtualizacaoEvento dadosAtualizacaoEvento){
        Turma turma = null;
        var evento = repository.getReferenceById(dadosAtualizacaoEvento.id());
        if (dadosAtualizacaoEvento.id_turma() != null){
         turma = turmaRepository.getReferenceById(dadosAtualizacaoEvento.id_turma());
        }
        evento.atualizarInfo(dadosAtualizacaoEvento,turma);
        return ResponseEntity.ok(new DadosEvento(evento));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Apaga um evento pelo seu ID",tags = {"Evento"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
    })
    public ResponseEntity apagarEvento( @PathVariable int id){
        var evento = repository.getReferenceById(id);
        repository.delete(evento);
        return ResponseEntity.noContent().build();
    }

    private String pegarExtensao(String nomeArquivo){
        int cont = nomeArquivo.lastIndexOf(".");
        return nomeArquivo.substring(cont+1);
    }
}
