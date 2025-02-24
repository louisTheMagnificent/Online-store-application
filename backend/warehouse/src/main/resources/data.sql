DROP TABLE IF EXISTS stock CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS storage CASCADE;

CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    product_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS storage (
    id BIGSERIAL PRIMARY KEY,
    customer BIGINT NOT NULL ,
    name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS stock(
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER NOT NULL,
    product_id BIGINT NOT NULL REFERENCES product,
    storage_id BIGINT NOT NULL REFERENCES storage
);

INSERT INTO product(description, price, product_name)
VALUES('Indulge in the rich, juicy taste of premium beef, sourced from the finest farms. ' ||
       'Our beef is tender, flavorful, and packed with nutrients, perfect for grilling, roasting, or gourmet cooking.',
       15, 'beef');

INSERT INTO product(description, price, product_name)
VALUES('Experience the rich taste of premium lamb, sourced from the finest farms. ' ||
       'Naturally tender and packed with protein & essential nutrients, our lamb is perfect for grilling, roasting, and gourmet dishes.',
       13, 'lamb');

INSERT INTO product(description, price, product_name)
VALUES('Crisp, sweet, and packed with vitamins & antioxidants, our premium apples are freshly picked for ultimate freshness. ' ||
       'Perfect for snacking, baking, or juicing, they deliver a delicious and healthy boost every day!',
       3, 'apple');

INSERT INTO product(description, price, product_name)
VALUES('Enjoy the delightfully crisp and juicy taste of our farm-fresh pears. ' ||
       'Packed with fiber, vitamins, and antioxidants, they are the perfect choice for snacking, baking, or salads.',
       7, 'pear');

INSERT INTO product(description, price, product_name)
VALUES('Experience advanced oral care with our premium toothpaste, designed to keep your teeth clean, strong, and cavity-free. With fluoride protection & fresh mint flavor,' ||
       ' it fights plaque, strengthens enamel, and gives you long-lasting freshness.',
       5, 'toothpaste');

INSERT INTO product(description, price, product_name)
VALUES('Spark imagination and creativity with our high-quality, safe, and exciting toys! Designed to entertain and educate, ' ||
       'our toys provide endless fun while promoting learning, motor skills, and creativity.',
       31, 'toy');

INSERT INTO storage(customer, name)
VALUES (2, 'Butcher first warehouse');

INSERT INTO storage(customer, name)
VALUES (2, 'Butcher second warehouse');

INSERT INTO storage(customer, name)
VALUES (3, 'Grocer first warehouse');

INSERT INTO storage(customer, name)
VALUES (3, 'Grocer second warehouse');

INSERT INTO storage(customer, name)
VALUES (4, 'Retailer first warehouse');

INSERT INTO storage(customer, name)
VALUES (4, 'Retailer second warehouse');

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (7, 1, 1);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (5, 1, 2);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (2, 2, 1);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (1, 2, 2);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (10, 3, 3);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (6, 3, 4);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (3, 4, 3);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (1, 4, 4);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (2, 5, 5);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (11, 5, 6);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (4, 6, 5);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (3, 6, 6);

INSERT INTO stock(quantity, product_id, storage_id)
VALUES (2, 5, 5);
