package br.com.escolar.dtos;


import br.com.escolar.entidades.Aluno;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EscolaDTO {
    private Long id;
    @NotBlank(message = "não pode estar em branco")
    private String nomeEscola;
    @NotBlank(message = "não pode estar em branco")
    private String telefoneEscola;

}
