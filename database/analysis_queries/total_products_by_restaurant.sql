
/*
=====================================================
Total Products by Restaurant
=====================================================
Query Purpose:
    This query returns each restaurant along with the total
    number of products associated with it.

    A LEFT JOIN is used to ensure that restaurants with no
    registered products are also included in the result,
    showing a product count of zero.
*/

SELECT
	r.restaurant_id_pk AS restaurant_id,
	COUNT(rp.res_pro_product_id_pk_fk) AS product_total
FROM restaurant AS r
LEFT JOIN restaurant_product AS rp
	ON r.restaurant_id_pk = rp.res_pro_restaurant_id_pk_fk
GROUP BY r.restaurant_id_pk