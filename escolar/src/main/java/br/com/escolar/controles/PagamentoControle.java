package br.com.escolar.controles;


import br.com.escolar.dtos.PagamentoDTO;
import br.com.escolar.servicos.PagamentoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PagamentoControle {

    private final PagamentoServico pagamentoServico;

    @PostMapping("/responsaveis/{responsavelId}")
    public ResponseEntity<PagamentoDTO>salvarPagamento(@PathVariable Long responsavelId, @RequestBody@Valid PagamentoDTO pagamentoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoServico.salvarPagamento(responsavelId,pagamentoDTO));
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoDTO>>listarPagamentos(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoServico.listarPagamentos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO>buscarPagamentoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoServico.buscarPagamentoPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PagamentoDTO>atualizarPagamento(@PathVariable Long id,@RequestBody @Valid  PagamentoDTO pagamentoDTO){
        return ResponseEntity.status(HttpStatus.OK).body(pagamentoServico.atualizarPagamentos(id,pagamentoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirPagamento(@PathVariable Long id){
        pagamentoServico.excluirPagamento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
