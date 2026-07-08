/*
=====================================================
Insert Sample Data
=====================================================
Script Purpose:
    This script populates the database with a small sample
    dataset for testing SQL queries and application features.

    The dataset includes customers, restaurants, products,
    delivery people, addresses, orders, and order items.

    Category tables are intentionally ignored, therefore all
    category foreign keys are set to NULL.
*/

-- ==========================================
-- Customer
-- ==========================================
INSERT INTO customer VALUES
('11111111111','John','Michael','Smith','11999990001','john@email.com','123'),
('22222222222','Emma','Grace','Johnson','11999990002','emma@email.com','123'),
('33333333333','Lucas','Henry','Brown','11999990003','lucas@email.com','123');

-- ==========================================
-- Delivery Person
-- ==========================================
INSERT INTO delivery_person VALUES
('44444444444','David','James','Wilson','11988880001','DSC1D79',1),
('55555555555','Sophia','Rose','Taylor','11988880002','FAH8D77',0);

-- ==========================================
-- Restaurant
-- ==========================================
INSERT INTO restaurant VALUES
('12345678000101','Pizza House','1133330001',NULL,'123'),
('12345678000102','Burger Place','1133330002',NULL,'123'),
('12345678000103','Sushi World','1133330003',NULL,'123');

-- ==========================================
-- Product
-- ==========================================
INSERT INTO product VALUES
(1,'Pepperoni Pizza','Large pepperoni pizza',NULL),
(2,'Cheese Pizza','Large cheese pizza',NULL),
(3,'Classic Burger','Beef burger with cheese',NULL),
(4,'French Fries','Crispy fries',NULL),
(5,'Salmon Sushi','8-piece salmon sushi',NULL),
(6,'Soda','350ml soft drink',NULL);

-- ==========================================
-- Customer Address
-- ==========================================
INSERT INTO customer_address VALUES
('08700000','11111111111','Oak Street',100),
('08700001','22222222222','Maple Avenue',55),
('08700002','33333333333','Pine Road',210);

-- ==========================================
-- Restaurant Address
-- ==========================================
INSERT INTO restaurant_address VALUES
('08710000','12345678000101','Main Avenue',10),
('08710001','12345678000102','Center Street',22),
('08710002','12345678000103','Liberty Avenue',35);

-- ==========================================
-- Restaurant Product
-- ==========================================
INSERT INTO restaurant_product VALUES
('12345678000101',1,30,49.90),
('12345678000101',2,20,44.90),
('12345678000101',6,80,6.50),

('12345678000102',3,25,29.90),
('12345678000102',4,40,12.50),
('12345678000102',6,60,5.50),

('12345678000103',5,35,54.90),
('12345678000103',6,40,7.00);

-- ==========================================
-- Orders
-- ==========================================
INSERT INTO orders
(order_status, delivery_person_id_fk, restaurant_id_fk, customer_id_fk)
VALUES
('Preparing',NULL,'12345678000101','11111111111'),
('Shipped','44444444444','12345678000101','22222222222'),
('Delivered','44444444444','12345678000102','33333333333'),
('Delivered','55555555555','12345678000103','11111111111'),
('Preparing',NULL,'12345678000102','22222222222');

-- ==========================================
-- Order Item
-- ==========================================
INSERT INTO order_item VALUES
(1,1,1),
(1,6,2),

(2,2,1),
(2,6,1),

(3,3,2),
(3,4,1),

(4,5,3),
(4,6,2),

(5,3,1),
(5,4,2),
(5,6,2);