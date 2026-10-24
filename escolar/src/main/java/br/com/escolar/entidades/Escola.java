package br.com.escolar.entidades;

import br.com.escolar.enums.Turno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Escola {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeEscola;
    private String telefoneEscola;
    @Enumerated(EnumType.STRING)
    @OneToMany(mappedBy = "escola",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Aluno>alunos = new ArrayList<>();
}
