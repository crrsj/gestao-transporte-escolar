package br.com.escolar.controles;

import br.com.escolar.dtos.EscolaDTO;
import br.com.escolar.servicos.EscolaServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/escolas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EscolaControle {

    private final EscolaServico escolaServico;

    @PostMapping
    public ResponseEntity<EscolaDTO>salvarEscola(@RequestBody @Valid EscolaDTO escolaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(escolaServico.salvarEscola(escolaDTO));
    }

    @GetMapping
    public ResponseEntity<Page<EscolaDTO>>listarEscolas(Pageable pageable){
       return ResponseEntity.status(HttpStatus.OK).body(escolaServico.listarEscolas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscolaDTO>buscarEscolaPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(escolaServico.buscarEscolaPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EscolaDTO>atualizarEscola(@PathVariable Long id,@RequestBody @Valid EscolaDTO escolaDTO){
        return ResponseEntity.status(HttpStatus.OK).body(escolaServico.atualizarEscola(id,escolaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirEscola(@PathVariable Long id){
        escolaServico.excluirEscola(id);
        return ResponseEntity.noContent().build();
    }
}
