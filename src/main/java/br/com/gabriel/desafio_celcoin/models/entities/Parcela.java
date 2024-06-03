package br.com.gabriel.desafio_celcoin.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "is_paga", nullable = false)
    private Boolean isPaga;

    @PrePersist
    public void prePersist() {
        this.isPaga = false;
    }

}
