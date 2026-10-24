package br.com.escolar.dtos;

import br.com.escolar.entidades.Responsavel;
import br.com.escolar.enums.Mes;
import br.com.escolar.enums.StatusPagamento;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class PagamentoDTO {
    private Long id;
    @NotBlank(message = "não pode estar em branco")
    private String pagador;
    private LocalDate dataPagamento;
    private int ano;
    private Mes mes;
    @NotNull(message = "O valor da mensalidade é obrigatório")
    @PositiveOrZero(message = "O valor da mensalidade não pode ser negativo")
    @Digits(integer = 6, fraction = 2, message = "O valor deve ter no máximo 6 dígitos inteiros e 2 casas decimais")
    private BigDecimal valorMensalidade;
    private StatusPagamento status;
    private Responsavel responsavel;

}
