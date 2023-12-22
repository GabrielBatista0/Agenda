package bosch.AgendaETS.controller;

import bosch.AgendaETS.model.turma.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.PreparedStatement;

@RestController
@RequestMapping("/turma")
public class TurmaController {
    @Autowired
    private TurmaRepository repository;

    @Transactional
    @PostMapping
    @Operation(summary = "Cadastrar turma",
            tags = {"Turma"})
    public ResponseEntity cadastrarTurma (@RequestBody @Valid DadosCadastroTurma dadosCadastroTurma, UriComponentsBuilder uriComponentsBuilder){
        var turma = new Turma(dadosCadastroTurma);
        repository.save(turma);
        var uri = uriComponentsBuilder.path("/turma/{id}").buildAndExpand(turma.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosTurma(turma));
    }

    @GetMapping
    @Operation(summary = "Lista todas as turmas",
            tags = {"Turma"})
    public ResponseEntity <Page<DadosTurma>> listarTurmas(@ParameterObject Pageable pageable){
        var lista = repository.findAllByAtivoTrue(pageable).map(DadosTurma::new);
        return ResponseEntity.ok(lista);
    }
    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza uma turma",
            tags = {"Turma"})
    public ResponseEntity atualizarTurma (@RequestBody @Valid DadosAtualizacaoTurma dadosAtualizacaoTurma){
        System.out.println(dadosAtualizacaoTurma.id());
        var turma = repository.getReferenceById(dadosAtualizacaoTurma.id());
        turma.atualizarInfo(dadosAtualizacaoTurma);
        return ResponseEntity.ok(new DadosTurma(turma));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deleta uma turma pelo ID",
            tags = {"Turma"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
    })
    public ResponseEntity desativarTurma( @PathVariable int id){
        var turma = repository.getReferenceById(id);
        turma.desativar();
        return ResponseEntity.noContent().build();
    }

}
