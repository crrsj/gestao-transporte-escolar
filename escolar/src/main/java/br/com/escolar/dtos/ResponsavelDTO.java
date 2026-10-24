package br.com.escolar.dtos;

import br.com.escolar.entidades.Aluno;
import br.com.escolar.entidades.Endereco;
import br.com.escolar.entidades.Pagamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;


@Data
public class ResponsavelDTO {
    private Long id;
    @NotBlank(message = "não pode estar em branco")
    private String nomeResponsavel;
    @CPF(message = "CPF inválido")
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;
    @NotBlank(message = "não pode estar em branco")
    private String telefone;
    @Email(message = "Insira um e-mail válido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;
    private List<Endereco> endereco = new ArrayList<>();
    private List<Aluno>alunos = new ArrayList<>();
    private List<Pagamento>pagamentos = new ArrayList<>();
}
