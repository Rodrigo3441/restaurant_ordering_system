-- Função do trigger
CREATE OR REPLACE FUNCTION fn_hist_res()
RETURNS TRIGGER AS
$$
BEGIN

    -- Nome
    IF NEW.res_nome <> OLD.res_nome THEN

        INSERT INTO HISTORICO_RESTAURANTE (
            fk_res_cnpj,
            hsr_campo_modificado,
            hsr_valor_antigo,
            hsr_valor_novo
        )
        VALUES (
            OLD.pk_res_cnpj,
            'res_nome',
            OLD.res_nome,
            NEW.res_nome
        );

    END IF;

    -- Telefone
    IF NEW.res_telefone <> OLD.res_telefone THEN

        INSERT INTO HISTORICO_RESTAURANTE (
            fk_res_cnpj,
            hsr_campo_modificado,
            hsr_valor_antigo,
            hsr_valor_novo
        )
        VALUES (
            OLD.pk_res_cnpj,
            'res_telefone',
            OLD.res_telefone,
            NEW.res_telefone
        );

    END IF;

    -- Categoria
    IF NEW.fk_res_id_catg <> OLD.fk_res_id_catg THEN

        INSERT INTO HISTORICO_RESTAURANTE (
            fk_res_cnpj,
            hsr_campo_modificado,
            hsr_valor_antigo,
            hsr_valor_novo
        )
        VALUES (
            OLD.pk_res_cnpj,
            'fk_res_id_catg',
            OLD.fk_res_id_catg,
            NEW.fk_res_id_catg
        );

    END IF;

    -- Senha
    IF NEW.res_senha <> OLD.res_senha THEN

        INSERT INTO HISTORICO_RESTAURANTE (
            fk_res_cnpj,
            hsr_campo_modificado,
            hsr_valor_antigo,
            hsr_valor_novo
        )
        VALUES (
            OLD.pk_res_cnpj,
            'res_senha',
            OLD.res_senha,
            NEW.res_senha
        );

    END IF;

    RETURN NEW;

END;
$$
LANGUAGE plpgsql;


-- Trigger
CREATE TRIGGER tg_hist_res
AFTER UPDATE ON RESTAURANTE
FOR EACH ROW
EXECUTE FUNCTION fn_hist_res();