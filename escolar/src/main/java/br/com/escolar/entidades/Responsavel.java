package br.com.escolar.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeResponsavel;
    @Column(unique = true)
    private String cpf;
    private String telefone;
    private String email;
    @OneToMany(mappedBy = "responsavel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Endereco>endereco = new ArrayList<>();
    @OneToMany(mappedBy = "responsavel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Aluno>alunos = new ArrayList<>();
    @OneToMany(mappedBy = "responsavel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Pagamento>pagamentos = new ArrayList<>();
}
