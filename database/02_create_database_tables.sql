/*
=====================================================
Create Database Tables
=====================================================
Script Purpose:
    This script creates the relational database structure for the restaurant
    ordering system. It defines all database tables, including their columns,
    primary keys, foreign keys, and relationships required by the application.

    The script should be executed after the database has been created and
    selected as the active database.

WARNING:
    Running this script on a database where these tables already exist
    will result in errors unless the existing tables are dropped beforehand.
    If recreating the database schema, ensure that any required data has been
    backed up before executing this script.
*/

-- ==========================================
-- Table: customer
-- ==========================================
CREATE TABLE IF NOT EXISTS customer (
	customer_id_pk VARCHAR(11) PRIMARY KEY,
	first_name VARCHAR(40) NOT NULL,
	middle_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    passcode VARCHAR(255) NOT NULL
);

-- ==========================================
-- Table: delivery_person
-- ==========================================
CREATE TABLE IF NOT EXISTS delivery_person (
	del_per_id_pk VARCHAR(11) PRIMARY KEY,  
	first_name VARCHAR(20) NOT NULL,
    middle_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    vehicle VARCHAR(7) NOT NULL,
    available SMALLINT NOT NULL
);

-- ==========================================
-- Table: restaurant_category
-- ==========================================
CREATE TABLE IF NOT EXISTS restaurant_category (
	res_cat_id_pk INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL, 
    description VARCHAR(255) NOT NULL
);

-- ==========================================
-- Table: product_category
-- ==========================================
CREATE TABLE IF NOT EXISTS product_category (
	pro_cat_id_pk INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- ==========================================
-- Table: restaurant
-- ========================================== 
CREATE TABLE IF NOT EXISTS restaurant (
	restaurant_id_pk VARCHAR (14) PRIMARY KEY,
    name VARCHAR (40) NOT NULL,
	phone VARCHAR (11) NOT NULL,
    cat_id_fk INT NULL,
    passcode VARCHAR (255) NOT NULL,
    
    FOREIGN KEY (cat_id_fk)
    REFERENCES restaurant_category(res_cat_id_pk)
);

-- ==========================================
-- Table: product
-- ==========================================
CREATE TABLE IF NOT EXISTS product (
	product_id_pk INT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR (255) NOT NULL,
    cat_id_fk INT NULL,

	FOREIGN KEY (cat_id_fk)
    REFERENCES product_category(pro_cat_id_pk)
);

-- ==========================================
-- Table: customer_address
-- ==========================================
CREATE TABLE IF NOT EXISTS customer_address (
	cus_add_postal_code_pk VARCHAR(8),
	cus_add_customer_id_pk_fk VARCHAR(11),
	name VARCHAR(100) NOT NULL,
	number INT NOT NULL,
	PRIMARY KEY (cus_add_postal_code_pk, cus_add_customer_id_pk_fk),
    
    FOREIGN KEY (cus_add_customer_id_pk_fk)
    REFERENCES customer(customer_id_pk)
);

-- ==========================================
-- Table: restaurant_address
-- ==========================================
CREATE TABLE IF NOT EXISTS restaurant_address (
	res_add_postal_code_pk VARCHAR(8),
	res_add_restaurant_id_pk_fk VARCHAR(14),
	name VARCHAR(100) NOT NULL,
	number INT NOT NULL,
	PRIMARY KEY (res_add_postal_code_pk, res_add_restaurant_id_pk_fk),
    
    FOREIGN KEY (res_add_restaurant_id_pk_fk)
    REFERENCES restaurant(restaurant_id_pk)
);



-- ==========================================
-- Table: restaurant_product
-- ==========================================
CREATE TABLE IF NOT EXISTS restaurant_product (
	res_pro_restaurant_id_pk_fk VARCHAR(14),
	res_pro_product_id_pk_fk INT,
	stock_amount INT NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (res_pro_restaurant_id_pk_fk, res_pro_product_id_pk_fk),
    
    FOREIGN KEY (res_pro_restaurant_id_pk_fk)
    REFERENCES restaurant(restaurant_id_pk),
    
    FOREIGN KEY (res_pro_product_id_pk_fk)
    REFERENCES product(product_id_pk)
);

-- ==========================================
-- Table: orders
-- ==========================================
CREATE TABLE IF NOT EXISTS orders (
	order_id_pk INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	order_status VARCHAR (15) NOT NULL,
	delivery_person_id_fk VARCHAR (11) NULL,
	restaurant_id_fk VARCHAR (14) NOT NULL,
	customer_id_fk VARCHAR (11) NOT NULL,
	order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (delivery_person_id_fk)
    REFERENCES delivery_person(del_per_id_pk),
    
    FOREIGN KEY (restaurant_id_fk)
    REFERENCES restaurant(restaurant_id_pk),
    
    FOREIGN KEY (customer_id_fk)
    REFERENCES customer(customer_id_pk)
);

-- ==========================================
-- Table: order_item
-- ==========================================
CREATE TABLE IF NOT EXISTS order_item (
	order_item_order_id_pk_fk INT,
	order_item_product_id_pk_fk INT,
	quantity INT NOT NULL, 
	PRIMARY KEY (order_item_order_id_pk_fk, order_item_product_id_pk_fk),

	FOREIGN KEY (order_item_order_id_pk_fk)
    REFERENCES orders(order_id_pk),
    
    FOREIGN KEY (order_item_product_id_pk_fk)
    REFERENCES product(product_id_pk)
);
