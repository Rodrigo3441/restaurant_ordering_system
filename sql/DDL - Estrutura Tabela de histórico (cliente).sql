-- Tabela de historiamento dos dados do cliente
CREATE TABLE HISTORICO_CLIENTE(
pk_hsc_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
fk_cli_cpf VARCHAR(11) NOT NULL,
hsc_campo_modificado VARCHAR(50),
hsc_valor_antigo VARCHAR(255),
hsc_valor_novo VARCHAR(255),
hsc_data_modificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (fk_cli_cpf)
REFERENCES CLIENTE(pk_cli_cpf)
)
