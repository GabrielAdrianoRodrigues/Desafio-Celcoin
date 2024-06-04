package br.com.gabriel.desafio_celcoin.models.entities;

import java.time.LocalDate;

import br.com.gabriel.desafio_celcoin.models.enums.DividaStatus;
import br.com.gabriel.desafio_celcoin.models.forms.DividaForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "bu_dividas")
public class Divida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "nm_credor", nullable = false)
    private String nomeCredor;

    @Column(name = "dt_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "qtd_parcelas", nullable = false)
    private Short numeroParcelas;

    @Enumerated
    @Column(name = "status", nullable = false)
    private DividaStatus status;

    public Divida(DividaForm form) {
        this.valorTotal = form.valorTotal();
        this.nomeCredor = form.nomeCredor();
        this.numeroParcelas = form.numeroParcelas();
    }

    @PrePersist
    public void prePersist() {
        this.status = DividaStatus.ATIVA;
    }
}
