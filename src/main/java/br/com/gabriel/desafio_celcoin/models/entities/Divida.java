package br.com.gabriel.desafio_celcoin.models.entities;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "bu_dividas")
@AllArgsConstructor@NoArgsConstructor
public class Divida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "nm_credor", nullable = false)
    private String nomeCredor;

    @Column(name = "qtd_parcelas", nullable = false)
    private Short numeroParcelas;

    @Column(name = "dt_venc_parcela", nullable = false)
    private Short diaVencimentoParcela;

    @Column(name = "taxa_juros", nullable = false)
    private Double taxaJuros;

    @Enumerated
    @Column(name = "div_status", nullable = false)
    private DividaStatus status;

    public Divida(DividaForm form) {
        this.valorTotal = form.valorTotal();
        this.nomeCredor = form.nomeCredor();
        this.numeroParcelas = form.numeroParcelas();
        this.diaVencimentoParcela = form.diaVencimentoParcela();
        this.taxaJuros = form.taxaJuros();
    }

    @PrePersist
    public void prePersist() {
        this.status = DividaStatus.ATIVA;
    }
}
