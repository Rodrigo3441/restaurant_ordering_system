CREATE OR REPLACE FUNCTION fn_audit_prod()
RETURNS TRIGGER
AS $$
BEGIN

	INSERT INTO AUDIT_PRODUTO(
		fk_prd_codigo,
		aup_prd_nome,
		aup_prd_descricao
	)
	VALUES (
		NEW.pk_prd_codigo,
		NEW.prd_nome,
		NEW.prd_descricao
	);

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tg_audit_prod
AFTER INSERT ON PRODUTO
FOR EACH ROW
EXECUTE FUNCTION fn_audit_prod();