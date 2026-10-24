package br.com.escolar.config;

import br.com.escolar.dtos.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepConfig {

        @GetMapping("/{cep}/json/")
        EnderecoDTO buscarEnderecoPor(@PathVariable("cep") String cep);

}
