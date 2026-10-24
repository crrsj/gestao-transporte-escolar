package br.com.escolar.dtos;


import br.com.escolar.entidades.Escola;
import br.com.escolar.entidades.Responsavel;
import br.com.escolar.enums.AnoLetivo;
import br.com.escolar.enums.Sexo;
import br.com.escolar.enums.TipoEnsino;
import br.com.escolar.enums.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlunoDTO {


    private Long id;
    @NotBlank(message = "Não pode estar em branco.")
    private String nomeAluno;
    @NotNull(message = "Não pode ser nulo.")
    private LocalDate dataNasc;
    private Sexo sexo;
    @NotNull(message = "Não pode ser nulo.")
    private String turma;
    private Turno turno;
    private TipoEnsino tipoEnsino;
    private AnoLetivo ano;
    private Responsavel responsavel;
    private Escola escola;
}
