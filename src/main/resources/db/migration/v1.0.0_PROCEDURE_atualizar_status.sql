CREATE EXTENSION pg_cron;

CREATE OR REPLACE PROCEDURE atualizarStatus() LANGUAGE plpgsql AS $$        
    BEGIN 
        UPDATE bu_parcelas SET
            par_status = 2
        WHERE dt_vencimento < now()
            AND par_status = 0;

        UPDATE bu_dividas bd SET
            div_status = 2
        WHERE bd.div_status = 0
            AND EXISTS(
                SELECT FROM bu_parcelas bp
                WHERE bp.par_status = 2 
                    AND bp.fk_divida_id = bu.id 
            );         
    END;
$$

SELECT cron.schedule('0 01 * * *', 'CALL atualizarStatus()');