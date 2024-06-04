CREATE TABLE bu_parcelas(
    id SERIAL PRIMARY KEY,
    fk_divida_id BIGINT NOT NULL
    valor_parcela DECIMAL(10, 0) NOT NULL,
    num_parcela SMALLINT NOT NULL
    is_paga BOOLEAN NOT NULL
    FOREIGN KEY (fk_divida_id) REFERENCES bu_dividas(id)
);