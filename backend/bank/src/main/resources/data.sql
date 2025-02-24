DROP TABLE IF EXISTS account;

CREATE TABLE account(
    id BIGINT PRIMARY KEY,
    balance DOUBLE PRECISION NOT NULL,
    customer_id BIGINT NOT NULL
);

INSERT INTO account(id, balance, customer_id)
VALUES (
    1, 100, 1
);

INSERT INTO account(id, balance, customer_id)
VALUES (
    2, 100, 2
);

INSERT INTO account(id, balance, customer_id)
VALUES(
    3, 100, 3
);

INSERT INTO account(id, balance, customer_id)
VALUES(
    4, 100, 4
);