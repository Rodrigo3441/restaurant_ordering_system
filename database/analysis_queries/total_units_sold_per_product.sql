/*
=====================================================
Total Units Sold per Product
=====================================================
Query Purpose:
    This query returns every product that has been sold in
    the system along with the total number of units sold.

    The total quantity sold for each product is calculated
    by summing the quantities recorded in the order_item
    table. The result is then joined with the product table
    to retrieve product information.

    The final result is ordered from the most sold product
    to the least sold product.
*/


SELECT
    oi.order_item_product_id_pk_fk AS product_id,
    p.name AS product_name,
    oi.total_sold
FROM
(
    SELECT
        order_item_product_id_pk_fk,
        SUM(quantity) AS total_sold
    FROM order_item
    GROUP BY order_item_product_id_pk_fk
) AS oi
INNER JOIN product AS p
    ON p.product_id_pk = oi.order_item_product_id_pk_fk
ORDER BY total_sold DESC;