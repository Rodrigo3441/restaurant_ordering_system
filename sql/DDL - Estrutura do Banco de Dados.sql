CREATE DATABASE Fred_Food;
USE Fred_Food;


-- Tabela Cliente
CREATE TABLE CLIENTE (
	pk_cli_cpf VARCHAR(11) PRIMARY KEY,
	cli_primeiro_nome VARCHAR(40) NOT NULL,
	cli_nome_meio VARCHAR(40) NOT NULL,
    cli_ultimo_nome VARCHAR(20) NOT NULL,
    cli_telefone VARCHAR(11) NOT NULL,
    cli_email VARCHAR(255) NOT NULL,
    cli_senha VARCHAR(255) NOT NULL
);

-- Tabela Entregador
CREATE TABLE ENTREGADOR (
	pk_etg_cpf VARCHAR(11) PRIMARY KEY,  
	etg_primeiro_nome VARCHAR(20) NOT NULL,
    etg_nome_meio VARCHAR(40) NOT NULL,
    etg_ultimo_nome VARCHAR(20) NOT NULL,
    etg_telefone VARCHAR(11) NOT NULL,
    etg_veiculo VARCHAR(7) NOT NULL,
    etg_disponibilidade SMALLINT NOT NULL
);

-- Tabela Categoria_Restaurante
CREATE TABLE CATEGORIA_RESTAURANTE (
	pk_res_id_catg INT PRIMARY KEY,
    ctr_nome VARCHAR(20) NOT NULL, 
    ctr_descricao VARCHAR(255) NOT NULL
);

-- Tabela Categoria_Produto
CREATE TABLE CATEGORIA_PRODUTO (
	pk_prd_id_catg INT PRIMARY KEY,
    ctp_nome VARCHAR(20) NOT NULL,
    ctp_descricao VARCHAR(255) NOT NULL
);

-- Tabela Restaurante 
CREATE TABLE RESTAURANTE (
	pk_res_cnpj VARCHAR (14) PRIMARY KEY,
    res_nome VARCHAR (40) NOT NULL,
	res_telefone VARCHAR (11) NOT NULL,
    fk_res_id_catg INT NULL,
    res_senha VARCHAR (255) NOT NULL,
    
    FOREIGN KEY (fk_res_id_catg)
    REFERENCES CATEGORIA_RESTAURANTE(pk_res_id_catg)
);

-- Tabela Produto
CREATE TABLE PRODUTO (
	pk_prd_codigo INT PRIMARY KEY,
    prd_nome VARCHAR(50) UNIQUE NOT NULL,
    prd_descricao VARCHAR (255) NOT NULL,
    fk_prd_id_catg INT NULL,

	FOREIGN KEY (fk_prd_id_catg)
    REFERENCES CATEGORIA_PRODUTO(pk_prd_id_catg)
);

-- Endereco_Cliente
CREATE TABLE ENDERECO_CLIENTE (
	pk_enc_cep VARCHAR(8),
	pk_fk_cli_cpf VARCHAR(11),
	enc_nome VARCHAR(100) NOT NULL,
	enc_numero INT NOT NULL,
	PRIMARY KEY (pk_enc_cep, pk_fk_cli_cpf),
    
    FOREIGN KEY (pk_fk_cli_cpf)
    REFERENCES CLIENTE(pk_cli_cpf)
);

-- Endereco_Restaurante
CREATE TABLE ENDERECO_RESTAURANTE (
	pk_enr_cep VARCHAR(8),
	pk_fk_res_cnpj VARCHAR(14),
	enr_nome VARCHAR(100) NOT NULL,
	enr_numero INT NOT NULL,
	PRIMARY KEY (pk_enr_cep, pk_fk_res_cnpj),
    
    FOREIGN KEY (pk_fk_res_cnpj)
    REFERENCES RESTAURANTE(pk_res_cnpj)
);



-- Produto_Restaurante
CREATE TABLE PRODUTO_RESTAURANTE (
	pk_fk_res_cnpj VARCHAR(14),
	pk_fk_prd_codigo INT,
	pdr_qtde_estoque INT NOT NULL,
	pdr_preco DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (pk_fk_res_cnpj, pk_fk_prd_codigo),
    
    FOREIGN KEY (pk_fk_res_cnpj)
    REFERENCES RESTAURANTE(pk_res_cnpj),
    
    FOREIGN KEY (pk_fk_prd_codigo)
    REFERENCES PRODUTO(pk_prd_codigo)
);

-- Pedido
CREATE TABLE PEDIDO (
	pk_ped_numero INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	ped_status VARCHAR (15) NOT NULL,
	fk_etg_cpf VARCHAR (11) NULL,
	fk_res_cnpj VARCHAR (14) NOT NULL,
	fk_cli_cpf VARCHAR (11) NOT NULL,
	ped_data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (fk_etg_cpf)
    REFERENCES ENTREGADOR(pk_etg_cpf),
    
    FOREIGN KEY (fk_res_cnpj)
    REFERENCES RESTAURANTE(pk_res_cnpj),
    
    FOREIGN KEY (fk_cli_cpf)
    REFERENCES CLIENTE(pk_cli_cpf)
);

-- Item_Pedido
CREATE TABLE ITEM_PEDIDO (
	pk_fk_ped_numero INT,
	pk_fk_prd_codigo INT,
	itp_quantidade INT NOT NULL, 
	PRIMARY KEY (pk_fk_ped_numero , pk_fk_prd_codigo),

	FOREIGN KEY (pk_fk_ped_numero)
    REFERENCES PEDIDO(pk_ped_numero),
    
    FOREIGN KEY (pk_fk_prd_codigo)
    REFERENCES PRODUTO(pk_prd_codigo)
);
