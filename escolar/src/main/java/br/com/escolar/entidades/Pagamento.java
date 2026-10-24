package br.com.escolar.entidades;

import br.com.escolar.enums.Mes;
import br.com.escolar.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pagador;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate dataPagamento;
    private int ano;
    @Enumerated(EnumType.STRING)
    private Mes mes;
    private BigDecimal valorMensalidade;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    @JsonIgnore
    private Responsavel responsavel;
}
