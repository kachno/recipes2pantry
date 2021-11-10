DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id     LONG PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    weight DOUBLE       NOT NULL,
    kcal   INT          NOT NULL
);