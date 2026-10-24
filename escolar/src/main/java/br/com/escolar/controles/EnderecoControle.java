package br.com.escolar.controles;


import br.com.escolar.dtos.EnderecoDTO;
import br.com.escolar.servicos.EnderecoServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnderecoControle {

    private final EnderecoServico enderecoServico;


    @PostMapping("responsaveis/{responsavelId}")
    public ResponseEntity<EnderecoDTO>salvarEndereco(@PathVariable Long responsavelId, @RequestBody @Valid EnderecoDTO enderecoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoServico.salvarEndereco(responsavelId,enderecoDTO));
    }


    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>>listarEnderecos(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoServico.listarEnderecos(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EnderecoDTO>atualizarEndereco(@PathVariable Long id, @RequestBody @Valid EnderecoDTO enderecoDTO){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoServico.atualizarEndereco(id, enderecoDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO>buscarEnderecoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoServico.buscarEnderecoPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirEndereco(@PathVariable Long id){
        enderecoServico.excluirEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
