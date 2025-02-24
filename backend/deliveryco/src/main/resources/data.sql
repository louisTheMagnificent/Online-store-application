DROP TABLE IF EXISTS order_table;

CREATE TABLE order_table(
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    item_quantities INTEGER NOT NULL,
    product_name varchar(255) NOT NULL,
    seller_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    time TIMESTAMP WITHOUT TIME ZONE NOT NULL ,
    total_price DOUBLE PRECISION NOT NULL
)