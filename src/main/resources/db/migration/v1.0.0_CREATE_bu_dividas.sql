CREATE TABLE bu_dividas(
    id SERIAL PRIMARY KEY,
    valor_total DECIMAL(10,2) NOT NULL,
    nm_credor VARCHAR(255) NOT NULL,
    dt_vencimento DATE NOT NULL,
    qtd_parcelas SMALLINT NOT NULL,
    is_paga BOOLEAN NOT NULL,
    is_ativa BOOLEAN NOT NULL
);