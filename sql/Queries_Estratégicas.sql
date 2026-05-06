-- consulta estratégia 01
-- retorna o restaurante e o total de produtos dele
SELECT
	r.pk_res_cnpj AS cnpj_do_restaurante,
	COUNT(*) AS total_produtos
FROM RESTAURANTE AS r
INNER JOIN PRODUTO_RESTAURANTE AS pr
	ON r.pk_res_cnpj = pr.pk_fk_res_cnpj
GROUP BY r.pk_res_cnpj;

-- consulta estratégia 02
-- retorna o número de pedidos que um cliente já fez + informações do mesmo
-- inclui somente clientes que compraram pelo menos uma vez
SELECT 
	c.pk_cli_cpf AS cpf_cliente,
	c.cli_primeiro_nome AS primeiro_nome ,
	c.cli_ultimo_nome AS sobrenome,
	p.total_pedidos
FROM 
	(
	SELECT
		fk_cli_cpf,
		COUNT(*) AS total_pedidos
	FROM PEDIDO
	GROUP BY fk_cli_cpf
	) AS p
INNER JOIN CLIENTE AS c
	ON p.fk_cli_cpf = c.pk_cli_cpf;


-- consulta estratégia 03
-- retorna o número de pedidos a serem entregues por um entregador + informações do mesmo
-- inclui somente entregadores que já saíram para entrega pelo menos 1 produto
SELECT
	e.pk_etg_cpf AS cpf_entregador,
	e.etg_primeiro_nome AS primeiro_nome,
	e.etg_ultimo_nome AS ultimo_nome,
	e.etg_veiculo AS veiculo_entregador,
	p.total_pedidos_entrega
FROM
	(
		SELECT
		fk_etg_cpf,
		COUNT(*) AS total_pedidos_entrega
		FROM PEDIDO
		WHERE ped_status = 'Saiu entrega'
		GROUP BY fk_etg_cpf
	) AS p
INNER JOIN ENTREGADOR AS e
ON p.fk_etg_cpf = e.pk_etg_cpf;

-- consulta estratégica 04
-- retorna o produto mais caro para cada restaurante + informações do restaurante e
-- o nome do produto também
SELECT
	cnpj_restaurante,
	nome_restaurante,
	nome_produto,
	produto_mais_caro
FROM
(
	SELECT
		*,
		MAX(pdr_preco) OVER(PARTITION BY cnpj_restaurante) AS produto_mais_caro
	FROM
		(	
		SELECT
			r.pk_res_cnpj AS cnpj_restaurante,
			r.res_nome AS nome_restaurante,
			p.prd_nome AS nome_produto,
			pr.pdr_preco
		FROM PRODUTO_RESTAURANTE AS PR
		INNER JOIN RESTAURANTE AS r
		ON r.pk_res_cnpj = pr.pk_fk_res_cnpj
		INNER JOIN PRODUTO AS p
		ON pr.pk_fk_prd_codigo = p.pk_prd_codigo
		) AS sub1
) AS sub2
WHERE pdr_preco = produto_mais_caro
ORDER BY produto_mais_caro DESC;

-- consulta estratégica 05
-- retorna todos os produtos já vendidos no sistema e os organiza pelos mais vendidos
SELECT 
	ip.pk_fk_prd_codigo AS codigo_produto,
	p.prd_nome AS nome_produto,
	ip.total_vendido
FROM
	(
	SELECT
		pk_fk_prd_codigo,
		SUM(itp_quantidade) AS total_vendido
	FROM ITEM_PEDIDO
	GROUP BY pk_fk_prd_codigo
	) AS ip
INNER JOIN PRODUTO AS p
	ON p.pk_prd_codigo = ip.pk_fk_prd_codigo
ORDER BY total_vendido DESC;