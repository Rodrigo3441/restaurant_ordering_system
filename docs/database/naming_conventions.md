# **Database Naming Conventions**

This document defines the naming conventions used for database objects, including tables, columns, primary keys, and foreign keys.

## **Table of Contents**

1. [General Principles](#general-principles)
2. [Table Naming Conventions](#table-naming-conventions)
3. [Column Naming Conventions](#column-naming-conventions)
4. [Abbreviations for FK and PK column names](#abbreviations-for-fk-and-pk-column-names)
---

## **General Principles**
- **Language**: Use English for all table names, columns, etc.
- **Naming Conventions**: Use snake_case, with lowercase letters and underscores (`_`) to separate words.
- **Language**: Use English for all names.
- **Avoid Reserved Words**: Do not use SQL reserved words as object names.
- **Table Names**: Prefer complete words over abbreviations.
- **Column Name Meaning**: Column names should describe the stored value rather than the data type.

## **Table Naming Conventions**

- All names should describe briefly what the table represents in the database.
  - Example: `customer_address` → the address of a customer.

## **Column Naming Conventions**

### **Primary Keys**  
- All primary keys in tables must use the name of the table as a prefix and the suffix `_pk`.
- **`<table_name>_<attribute>_pk`**  
  - `<table_name>`: Refers to the name of the table or entity the primary key belongs to.  
  - `<attribute>`: Refers to the unique identifier that the column uses.
  - `_pk`: A suffix indicating that this column is a primary key.  
  - Example: `customer_cpf_pk` → Primary key in the `customers` table that uses the CPF (ID) of the customer.
  
### **Foreign Keys**
- All Foreign Keys must end with the suffix `_fk`.
- **`<attribute>_fk`**  
  - `<attribute>`: Refers to the column the fk is linked to.  
  - `_fk`: A suffix indicating that this column is a foreign key.  
  - Example: `order_item_product_code_fk` → A foreign key that is linked to the table `order_item` on the column `product_code`

## **Abbreviations for FK and PK column names**
- Abbreviations are allowed, as long as they can be understood.

### Examples:
- Instead of `restaurant_address_postal_code_fk`, use `res_add_postal_code_fk`, in order to make it shorter

### Rules
- Always keep at least three letters from each table name.
- Use this approach mainly for tables with a compound name (at least two words)
- Avoid using abbreviations for tables such as: `customer`, `restaurant`, and etc.
- Recommended use for abbreviations: `customer_address`, `restaurant_address`, `restaurant_category`, and etc.
