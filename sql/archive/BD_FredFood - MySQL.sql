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
    prd_nome VARCHAR(50) NOT NULL,
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
	pk_ped_numero INT AUTO_INCREMENT PRIMARY KEY,
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
select * from PEDIDO;

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

SHOW TABLES;

USE Fred_Food;

-- CLIENTES
INSERT INTO CLIENTE VALUES
('12345678901','Joao','Silva','Souza','51999990001','joao@gmail.com','123'),
('98765432100','Maria','Oliveira','Costa','51999990002','maria@gmail.com','123'),
('11122233344','Pedro','Alves','Pereira','51999990003','pedro@gmail.com','123');

-- ENTREGADORES
INSERT INTO ENTREGADOR VALUES
('22233344455','Carlos','M','Ferreira','51988880001','ABC1234',1),
('33344455566','Ana','L','Rodrigues','51988880002','XYZ5678',1);

-- CATEGORIA RESTAURANTE
INSERT INTO CATEGORIA_RESTAURANTE VALUES
(1,'Lanches','Hamburgueria e fast food'),
(2,'Pizza','Pizzarias em geral'),
(3,'Japonesa','Comida japonesa');

-- CATEGORIA PRODUTO
INSERT INTO CATEGORIA_PRODUTO VALUES
(1,'Hamburguer','Lanches diversos'),
(2,'Pizza','Pizzas salgadas e doces'),
(3,'Sushi','Comida japonesa');

-- RESTAURANTES
INSERT INTO RESTAURANTE VALUES
('12345678000199','Burger House','5133330001',1,'123'),
('98765432000188','Pizzaria Napoli','5133330002',2,'123'),
('45678912000177','Sushi Zen','5133330003',3,'123');

-- PRODUTOS
INSERT INTO PRODUTO VALUES
(1,'X-Burguer','Hamburguer com queijo',1),
(2,'X-Salada','Hamburguer com salada',1),
(3,'Pizza Calabresa','Pizza tradicional',2),
(4,'Pizza Chocolate','Pizza doce',2),
(5,'Sushi Combo','Combo 20 peças',3);

-- ENDERECO CLIENTE
INSERT INTO ENDERECO_CLIENTE VALUES
('90000000','12345678901','Rua A',100),
('90000001','98765432100','Rua B',200),
('90000002','11122233344','Rua C',300);

-- ENDERECO RESTAURANTE
INSERT INTO ENDERECO_RESTAURANTE VALUES
('90010000','12345678000199','Av Central',500),
('90010001','98765432000188','Av Italia',600),
('90010002','45678912000177','Av Japão',700);

-- PRODUTO_RESTAURANTE
INSERT INTO PRODUTO_RESTAURANTE VALUES
('12345678000199',1,50,25.00),
('12345678000199',2,40,28.00),
('98765432000188',3,30,45.00),
('98765432000188',4,20,40.00),
('45678912000177',5,25,60.00);

-- PEDIDOS
INSERT INTO PEDIDO (ped_status, fk_etg_cpf, fk_res_cnpj, fk_cli_cpf) VALUES
('Entregue','22233344455','12345678000199','12345678901'),
('Em preparo','33344455566','98765432000188','98765432100'),
('Saiu entrega','22233344455','45678912000177','11122233344');

-- ITENS DO PEDIDO
INSERT INTO ITEM_PEDIDO VALUES
(1,1,2),
(1,2,1),
(2,3,1),
(3,5,2);


SELECT * FROM CLIENTE;
SELECT * FROM PEDIDO;
SELECT * FROM RESTAURANTE;
SELECT * FROM ENTREGADOR;
SELECT * FROM ENDERECO_RESTAURANTE;
SELECT * FROM PRODUTO;
SELECT * FROM CATEGORIA_PRODUTO;
SELECT * FROM CATEGORIA_RESTAURANTE;
SELECT * FROM ITEM_PEDIDO;
SELECT * FROM ENDERECO_CLIENTE;
SELECT * FROM PRODUTO_RESTAURANTE;

SELECT * FROM PRODUTO_RESTAURANTE;

-- Query pra ver quantos produtos cada restaurante possui
SELECT 
pk_fk_res_cnpj,
COUNT(*) AS totalProdutos
FROM PRODUTO_RESTAURANTE
GROUP BY pk_fk_res_cnpj;

SELECT
c.pk_cli_cpf,
p.pk_ped_numero
FROM CLIENTE AS c
INNER JOIN PEDIDO AS p
ON c.pk_cli_cpf = p.fk_cli_cpf;

INSERT INTO RESTAURANTE VALUES 
('12345678000101', 'Pizzaria Napoli', '11987654321', 1, 'senha123'),
('98765432000199', 'Burger House', '11991234567', 2, 'senha456'),
('45678912000155', 'Sushi Express', '11999887766', 3, 'senha789'),
('32165498000122', 'Churrascaria Boi Forte', '11993456789', 1, 'senhaabc'),
('15975348000177', 'Veggie Life', '11992345678', NULL, 'senhaxyz');
