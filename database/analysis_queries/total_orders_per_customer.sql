/*
=====================================================
Total Orders per Customer
=====================================================
Query Purpose:
    This query returns each customer along with the total
    number of orders they have placed.

    The order count is calculated in a derived table and then
    joined with the customer table to retrieve customer
    information.

    Only customers who have placed at least one order are
    included in the result.
*/

SELECT
	p.customer_id,
	c.first_name,
	c.last_name,
	p.total_orders
FROM 
	(
	SELECT
		customer_id_fk AS customer_id,
		COUNT(*) AS total_orders
	FROM orders
	GROUP BY customer_id_fk
	) AS p
INNER JOIN customer AS c
	ON p.customer_id = c.customer_id_pk
