/*
=====================================================
Most Expensive Product per Restaurant
=====================================================
Query Purpose:
    This query returns the most expensive product offered by
    each restaurant, along with the restaurant identifier,
    restaurant name, product name, and product price.

    A window function is used to determine the highest-priced
    product within each restaurant. If multiple products share
    the highest price in the same restaurant, all of them are
    included in the result.

    The final result is ordered by product price in
    descending order.
*/

SELECT
    restaurant_id,
    restaurant_name,
    product_name,
    most_expensive_product
FROM
(
    SELECT
        *,
        MAX(price) OVER (PARTITION BY restaurant_id) AS most_expensive_product
    FROM
    (
        SELECT
            r.restaurant_id_pk AS restaurant_id,
            r.name AS restaurant_name,
            p.name AS product_name,
            rp.price
        FROM restaurant_product AS rp
        INNER JOIN restaurant AS r
            ON r.restaurant_id_pk = rp.res_pro_restaurant_id_pk_fk
        INNER JOIN product AS p
            ON p.product_id_pk = rp.res_pro_product_id_pk_fk
    ) AS sub1
) AS sub2
WHERE price = most_expensive_product
ORDER BY most_expensive_product DESC;