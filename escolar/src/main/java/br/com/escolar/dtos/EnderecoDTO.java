package br.com.escolar.dtos;

import br.com.escolar.entidades.Responsavel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    private Long id;
    @NotBlank(message = "não pode estar em branco")
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String complemento;
    private String uf;
    private String estado;
    private Responsavel responsavel;
}
