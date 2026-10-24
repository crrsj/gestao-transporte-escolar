package br.com.escolar.controles;


import br.com.escolar.dtos.ResponsavelDTO;
import br.com.escolar.dtos.ResponsavelDtoSemCpf;
import br.com.escolar.servicos.ResponsavelServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsaveis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResponsavelControle {

    private final ResponsavelServico responsavelServico;

    @PostMapping
    public ResponseEntity<ResponsavelDtoSemCpf>salvarResponsavel(@RequestBody @Valid ResponsavelDTO responsavelDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelServico.salvarResponsavel(responsavelDTO));
    }

    @GetMapping("/cpf")
    public ResponseEntity<ResponsavelDtoSemCpf>buscarResponsavelPorCpf(@RequestParam String cpf){
        return ResponseEntity.status(HttpStatus.OK).body(responsavelServico.buscarPorCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<Page<ResponsavelDtoSemCpf>>listarResponsaveis(
            @PageableDefault(size = 10, sort = "nomeResponsavel", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(responsavelServico.listarResponsaveis(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponsavelDtoSemCpf>atualizarResponsavel(@PathVariable Long id,@RequestBody @Valid ResponsavelDtoSemCpf responsavelDtoSemCpf){
        return ResponseEntity.status(HttpStatus.OK).body(responsavelServico.atualizarResponsavel(id, responsavelDtoSemCpf));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelDtoSemCpf>buscarResponsavelPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(responsavelServico.buscarResponsavelPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirResponsavel(@PathVariable Long id) {
        responsavelServico.excluirResponsavel(id);
        return ResponseEntity.noContent().build();

    }
}
