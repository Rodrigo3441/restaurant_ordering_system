-- Função do trigger para que possa ser criada o trigger posteriormente
CREATE OR REPLACE FUNCTION fn_hist_cli()
RETURNS TRIGGER AS
$$
BEGIN

    -- Primeiro nome
    IF NEW.cli_primeiro_nome != OLD.cli_primeiro_nome THEN

        INSERT INTO HISTORICO_CLIENTE (
            fk_cli_cpf,
            hsc_campo_modificado,
            hsc_valor_antigo,
            hsc_valor_novo
        )
        VALUES (
            OLD.pk_cli_cpf,
            'cli_primeiro_nome',
            OLD.cli_primeiro_nome,
            NEW.cli_primeiro_nome
        );

    END IF;

    -- Nome meio
    IF NEW.cli_nome_meio != OLD.cli_nome_meio THEN

        INSERT INTO HISTORICO_CLIENTE (
            fk_cli_cpf,
            hsc_campo_modificado,
            hsc_valor_antigo,
            hsc_valor_novo
        )
        VALUES (
            OLD.pk_cli_cpf,
            'cli_nome_meio',
            OLD.cli_nome_meio,
            NEW.cli_nome_meio
        );

    END IF;

    -- Último nome
    IF NEW.cli_ultimo_nome != OLD.cli_ultimo_nome THEN

        INSERT INTO HISTORICO_CLIENTE (
            fk_cli_cpf,
            hsc_campo_modificado,
            hsc_valor_antigo,
            hsc_valor_novo
        )
        VALUES (
            OLD.pk_cli_cpf,
            'cli_ultimo_nome',
            OLD.cli_ultimo_nome,
            NEW.cli_ultimo_nome
        );

    END IF;

    -- Telefone
    IF NEW.cli_telefone != OLD.cli_telefone THEN

        INSERT INTO HISTORICO_CLIENTE (
            fk_cli_cpf,
            hsc_campo_modificado,
            hsc_valor_antigo,
            hsc_valor_novo
        )
        VALUES (
            OLD.pk_cli_cpf,
            'cli_telefone',
            OLD.cli_telefone,
            NEW.cli_telefone
        );

    END IF;

    -- Email
    IF NEW.cli_email != OLD.cli_email THEN

        INSERT INTO HISTORICO_CLIENTE (
            fk_cli_cpf,
            hsc_campo_modificado,
            hsc_valor_antigo,
            hsc_valor_novo
        )
        VALUES (
            OLD.pk_cli_cpf,
            'cli_email',
            OLD.cli_email,
            NEW.cli_email
        );

    END IF;

    -- Senha
    IF NEW.cli_senha != OLD.cli_senha THEN

        INSERT INTO HISTORICO_CLIENTE (
            fk_cli_cpf,
            hsc_campo_modificado,
            hsc_valor_antigo,
            hsc_valor_novo
        )
        VALUES (
            OLD.pk_cli_cpf,
            'cli_senha',
            OLD.cli_senha,
            NEW.cli_senha
        );

    END IF;

    RETURN NEW;

END;
$$
LANGUAGE plpgsql

CREATE TRIGGER tg_hist_cli
AFTER UPDATE ON CLIENTE
FOR EACH ROW
EXECUTE FUNCTION fn_hist_cli();