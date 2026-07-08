/*
=====================================================
Total Shipped Orders per Delivery Person
=====================================================
Query Purpose:
    This query returns each delivery person along with the
    total number of orders currently marked as 'Shipped'.

    The shipment count is calculated in a derived table and
    then joined with the delivery_person table to retrieve
    delivery person information.

    Only delivery persons assigned to at least one shipped
    order are included in the result.
*/


SELECT
    dp.del_per_id_pk AS delivery_person_id,
    dp.first_name,
    dp.last_name,
    dp.vehicle,
    p.total_deliveries
FROM
(
    SELECT
        delivery_person_id_fk,
        COUNT(*) AS total_deliveries
    FROM orders
    WHERE order_status = 'Shipped'
    GROUP BY delivery_person_id_fk
) AS p
INNER JOIN delivery_person AS dp
    ON p.delivery_person_id_fk = dp.del_per_id_pk;