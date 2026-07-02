# Data Dictionary for database

## Overview
This data dictionary describes the database structure of the Restaurant Ordering System. It provides information about each table, including its purpose, columns, data types, keys, constraints, and relationships with other tables.

The database is designed to support the main operations of a restaurant, such as customer management, employee management, menu organization, order processing, payments, and inventory control. The data model follows relational database principles to ensure data consistency, integrity, and efficient retrieval of information.

This document serves as a reference for developers, database administrators, and other stakeholders involved in the development, maintenance, and use of the system.

---

### 1. **customer**
- **Purpose:** Stores customers information.
- **Columns:**

| Column Name    | Data Type | Size | Nullable | Key | References | Description                                                                 |
| -------------- | --------- | ---- | -------- | --- | ---------- | --------------------------------------------------------------------------- |
| customer_id_pk | VARCHAR   | 11   | No       | PK  | —          | Unique identifier assigned to each customer.                                |
| first_name     | VARCHAR   | 40   | No       | —   | —          | Customer's first name.                                                      |
| middle_name    | VARCHAR   | 40   | No       | —   | —          | Customer's middle name.                                                     |
| last_name      | VARCHAR   | 20   | No       | —   | —          | Customer's last name.                                                       |
| phone          | VARCHAR   | 11   | No       | —   | —          | Customer's phone number.                                                    |
| email          | VARCHAR   | 255  | No       | —   | —          | Customer's email address used for communication and account identification. |
| passcode       | VARCHAR   | 255  | No       | —   | —          | Customer's password used for user authentication. Stored as plain text in this educational project. |


---

### 2. **delivery_person**
- **Purpose:** Stores deliverers information.
- **Columns:**

| Column Name   | Data Type | Size | Nullable | Key | References | Description                                                                                                           |
| ------------- | --------- | ---- | -------- | --- | ---------- | ------------------------------------------------------------------------------------------------------------------- |
| del_per_id_pk | VARCHAR   | 11   | No       | PK  | —          | Unique identifier assigned to each delivery person.                                                                 |
| first_name    | VARCHAR   | 20   | No       | —   | —          | Delivery person's first name.                                                                                       |
| middle_name   | VARCHAR   | 40   | No       | —   | —          | Delivery person's middle name.                                                                                      |
| last_name     | VARCHAR   | 20   | No       | —   | —          | Delivery person's last name.                                                                                        |
| phone         | VARCHAR   | 11   | No       | —   | —          | Delivery person's phone number.                                                                                     |
| vehicle       | VARCHAR   | 7    | No       | —   | —          | Identifier of the vehicle used for deliveries.                                                              |
| available     | SMALLINT  | —    | No       | —   | —          | Indicates whether the delivery person is available to accept deliveries (e.g., `0` = available, `1` = unavailable). |


---

### 3. **restaurant_category**
- **Purpose:** Stores information about the restaurant category (unused).
- **Columns:**

| Column Name   | Data Type | Size | Nullable | Key | References | Description                                             |
| ------------- | --------- | ---- | -------- | --- | ---------- | ------------------------------------------------------- |
| res_cat_id_pk | INT       | —    | No       | PK  | —          | Unique identifier assigned to each restaurant category. |
| name          | VARCHAR   | 20   | No       | —   | —          | Name of the restaurant category.                        |
| description   | VARCHAR   | 255  | No       | —   | —          | Brief description of the restaurant category.           |

---

### 4. **product_category**
- **Purpose:** Stores information about the product category (unused).
- **Columns:**

| Column Name   | Data Type | Size | Nullable | Key | References | Description                                          |
| ------------- | --------- | ---- | -------- | --- | ---------- | ---------------------------------------------------- |
| pro_cat_id_pk | INT       | —    | No       | PK  | —          | Unique identifier assigned to each product category. |
| name          | VARCHAR   | 20   | No       | —   | —          | Name of the product category.                        |
| description   | VARCHAR   | 255  | No       | —   | —          | Brief description of the product category.           |

---

### 5. **restaurant**
- **Purpose:** Stores information the restaurants.
- **Columns:**

| Column Name      | Data Type | Size | Nullable | Key | References                           | Description                                                      |
| ---------------- | --------- | ---- | -------- | --- | ------------------------------------ | ---------------------------------------------------------------- |
| restaurant_id_pk | VARCHAR   | 14   | No       | PK  | —                                    | Unique identifier assigned to each restaurant.                   |
| name             | VARCHAR   | 40   | No       | —   | —                                    | Name of the restaurant.                                          |
| phone            | VARCHAR   | 11   | No       | —   | —                                    | Restaurant's contact phone number.                               |
| cat_id_fk        | INT       | —    | Yes      | FK  | `restaurant_category(res_cat_id_pk)` | Identifies the category to which the restaurant belongs.         |
| passcode         | VARCHAR   | 255  | No       | —   | —                                    | Restaurant's password used to authenticate access to the system. |

---

### 6. **product**
- **Purpose:** Stores information about the products.
- **Columns:**

| Column Name   | Data Type | Size | Nullable | Key | References                        | Description                                            |
| ------------- | --------- | ---- | -------- | --- | --------------------------------- | ------------------------------------------------------ |
| product_id_pk | INT       | —    | No       | PK  | —                                 | Unique identifier assigned to each product.            |
| name          | VARCHAR   | 50   | No       | —   | —                                 | Name of the product. Must be unique within the system. |
| description   | VARCHAR   | 255  | No       | —   | —                                 | Brief description of the product.                      |
| cat_id_fk     | INT       | —    | Yes      | FK  | `product_category(pro_cat_id_pk)` | Identifies the category to which the product belongs.  |

---

### 7. **customer_address**
- **Purpose:** Stores customer additional information about their address.
- **Columns:**

| Column Name               | Data Type | Size | Nullable | Key    | References                 | Description                                                                             |
| ------------------------- | --------- | ---- | -------- | ------ | -------------------------- | --------------------------------------------------------------------------------------- |
| cus_add_postal_code_pk    | VARCHAR   | 8    | No       | PK     | —                          | Postal code of the customer's address. Part of the composite primary key.               |
| cus_add_customer_id_pk_fk | VARCHAR   | 11   | No       | PK, FK | `customer(customer_id_pk)` | Identifies the customer associated with the address. Part of the composite primary key. |
| name                      | VARCHAR   | 100  | No       | —      | —                          | Name of the street or address location.                                                 |
| number                    | INT       | —    | No       | —      | —                          | Street number of the customer's address.                                                |

---

### 8. **restaurant_address**
- **Purpose:** Stores restaurant additional information about their address.
- **Columns:**

| Column Name                 | Data Type | Size | Nullable | Key    | References                   | Description                                                                               |
| --------------------------- | --------- | ---- | -------- | ------ | ---------------------------- | ----------------------------------------------------------------------------------------- |
| res_add_postal_code_pk      | VARCHAR   | 8    | No       | PK     | —                            | Postal code of the restaurant's address. Part of the composite primary key.               |
| res_add_restaurant_id_pk_fk | VARCHAR   | 14   | No       | PK, FK | restaurant(restaurant_id_pk) | Identifies the restaurant associated with the address. Part of the composite primary key. |
| name                        | VARCHAR   | 100  | No       | —      | —                            | Name of the street or address location.                                                   |
| number                      | INT       | —    | No       | —      | —                            | Street number of the restaurant's address.                                                |

---

### 9. **restaurant_product**
- **Purpose:** Stores the products that are associated to the restaurant (associative table).
- **Columns:**

| Column Name                 | Data Type | Size | Nullable | Key    | References                     | Description                                                                           |
| --------------------------- | --------- | ---- | -------- | ------ | ------------------------------ | ------------------------------------------------------------------------------------- |
| res_pro_restaurant_id_pk_fk | VARCHAR   | 14   | No       | PK, FK | `restaurant(restaurant_id_pk)` | Identifies the restaurant that offers the product. Part of the composite primary key. |
| res_pro_product_id_pk_fk    | INT       | —    | No       | PK, FK | `product(product_id_pk)`       | Identifies the product offered by the restaurant. Part of the composite primary key.  |
| stock_amount                | INT       | —    | No       | —      | —                              | Quantity of the product currently available in stock.                                 |
| price                       | DECIMAL   | 10,2 | No       | —      | —                              | Selling price of the product at the restaurant.                                       |

---

### 10. **orders**
- **Purpose:** Stores transactional information about orders.
- **Columns:**

| Column Name           | Data Type | Size | Nullable | Key | References                       | Description                                                                        |
| --------------------- | --------- | ---- | -------- | --- | -------------------------------- | ---------------------------------------------------------------------------------- |
| order_id_pk           | INT       | —    | No       | PK  | —                                | Unique identifier assigned to each order. Automatically generated by the database. |
| order_status          | VARCHAR   | 15   | No       | —   | —                                | Current status of the order (e.g., *Preparing*, *Shipped*, *Delivered*).           |
| delivery_person_id_fk | VARCHAR   | 11   | Yes      | FK  | `delivery_person(del_per_id_pk)` | Identifies the delivery person assigned to the order.                              |
| restaurant_id_fk      | VARCHAR   | 14   | No       | FK  | `restaurant(restaurant_id_pk)`   | Identifies the restaurant responsible for the order.                               |
| customer_id_fk        | VARCHAR   | 11   | No       | FK  | `customer(customer_id_pk)`       | Identifies the customer who placed the order.                                      |
| order_date            | TIMESTAMP | —    | Yes      | —   | —                                | Date and time when the order was created. Defaults to the current timestamp.       |

---

### 11. **order_item**
- **Purpose:** Stores the products that are associated to an order (associative table).
- **Columns:**

| Column Name                 | Data Type | Size | Nullable | Key    | References               | Description                                                                       |
| --------------------------- | --------- | ---- | -------- | ------ | ------------------------ | --------------------------------------------------------------------------------- |
| order_item_order_id_pk_fk   | INT       | —    | No       | PK, FK | `orders(order_id_pk)`    | Identifies the order associated with the item. Part of the composite primary key. |
| order_item_product_id_pk_fk | INT       | —    | No       | PK, FK | `product(product_id_pk)` | Identifies the product included in the order. Part of the composite primary key.  |
| quantity                    | INT       | —    | No       | —      | —                        | Quantity of the specified product ordered.                                        |