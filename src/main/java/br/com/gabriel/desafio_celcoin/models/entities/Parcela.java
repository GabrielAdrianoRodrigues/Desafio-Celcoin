package br.com.gabriel.desafio_celcoin.models.entities;

import java.time.LocalDate;

import br.com.gabriel.desafio_celcoin.models.enums.ParcelaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "bu_parcelas")
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fk_divida_id", nullable = false)
    private Long dividaId;

    @Column(name = "valor_parcela", nullable = false)
    private Double valorParcela;

    @Column(name = "num_parcela", nullable = false)
    private Short numParcela;

    @Column(name = "valor_juros")
    private Double valorJuros;

    @Column(name = "dt_vencimento", nullable = false)
    private LocalDate dataVencimento; 

    @Enumerated
    @Column(name = "status", nullable = false)
    private ParcelaStatus status;

    @PrePersist
    public void prePersist() {
        this.valorJuros =  0.0;
        this.status = ParcelaStatus.ABERTA;
    }

}
