CREATE TABLE IF NOT EXISTS bu_dividas (
    id SERIAL PRIMARY KEY,
    valor_total DECIMAL(10,2) NOT NULL,
    nm_credor VARCHAR(255) NOT NULL,
    qtd_parcelas SMALLINT NOT NULL,
    dt_venc_parcela SMALLINT,
    taxa_juros DECIMAL(10,2) NOT NULL,
    div_status SMALLINT NOT NULL
);