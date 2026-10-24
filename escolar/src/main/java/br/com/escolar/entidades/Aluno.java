package br.com.escolar.entidades;

import br.com.escolar.enums.AnoLetivo;
import br.com.escolar.enums.Sexo;
import br.com.escolar.enums.TipoEnsino;
import br.com.escolar.enums.Turno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeAluno;
    private LocalDate dataNasc;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @NotBlank(message = "não pode estar em branco")
    private String turma;
    @Enumerated(EnumType.STRING)
    private Turno turno;
    @Enumerated(EnumType.STRING)
    private TipoEnsino tipoEnsino;
    @Enumerated(EnumType.STRING)
    private AnoLetivo ano;
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    @JsonIgnore
    private Responsavel responsavel;
    @ManyToOne
    @JoinColumn(name = "escola_id")
    @JsonIgnore
    private Escola escola;
}
