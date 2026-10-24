package br.com.escolar.controles;

import br.com.escolar.dtos.AlunoDTO;
import br.com.escolar.servicos.AlunoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlunoControle {

    private final AlunoServico alunoServico;

    @PostMapping("/responsaveis/{responsavelId}/escolas/{escolaId}")
    public ResponseEntity<AlunoDTO>salvarAluno(@PathVariable Long responsavelId,
                                               @PathVariable Long escolaId ,
                                               @RequestBody @Valid AlunoDTO alunoDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alunoServico.salvarAluno(responsavelId,escolaId, alunoDTO));
    }

    @GetMapping
    public ResponseEntity<Page<AlunoDTO>>listarAlunos(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(alunoServico.listarAlunos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO>buscarAlunoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(alunoServico.buscarAlunoPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlunoDTO>atualizarAlunos(@PathVariable Long id,@RequestBody @Valid AlunoDTO alunoDTO){
        return ResponseEntity.status(HttpStatus.OK).body(alunoServico.atualizarAluno(id, alunoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirAluno(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }
}
