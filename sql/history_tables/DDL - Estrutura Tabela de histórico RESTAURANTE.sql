-- Tabela de historiamento dos dados do restaurante
CREATE TABLE HISTORICO_RESTAURANTE(
pk_hsr_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
fk_res_cnpj VARCHAR(11) NOT NULL,
hsr_campo_modificado VARCHAR(50),
hsr_valor_antigo VARCHAR(255),
hsr_valor_novo VARCHAR(255),
hsr_data_modificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

FOREIGN KEY (fk_res_cnpj)
REFERENCES RESTAURANTE(pk_res_cnpj)
)