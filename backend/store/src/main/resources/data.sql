DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    is_logged_in BOOLEAN NOT NULL,
    is_seller BOOLEAN NOT NULL,
    username VARCHAR(255) NOT NULL
);

INSERT INTO customer(email, hashed_password, is_logged_in, is_seller, username)
VALUES('customer@gmail.com', '88c46ef2d305886d17911c6d9c47dbec2a386fe9c6655c2c1079707ae4c61209',
       false, false, 'customer');

INSERT INTO customer(email, hashed_password, is_logged_in, is_seller, username)
VALUES('butcher@gmail.com', '01303d3beb16b992be93426c567ae5d5adeb1981d9a1063c9194752e9f284b9d',
       false, true, 'butcher');

INSERT INTO customer(email, hashed_password, is_logged_in, is_seller, username)
VALUES('grocer@gmail.com', 'adfa2e566afb919d856543534fe0de002d241c23a496bb3bccd18a94052c9cf2',
       false, true, 'grocer');

INSERT INTO customer(email, hashed_password, is_logged_in, is_seller, username)
VALUES('retailer@gmail.com', '36b2a10044ffe600e9163e1acb171c112a1065d698cc012ef9b87cb751ed9361',
       false, true, 'retailer');