DROP TABLE IF EXISTS stocks;
CREATE TABLE stocks
(
    id         LONG PRIMARY KEY AUTO_INCREMENT,
    count      INT  NOT NULL,
    product_id LONG NOT NULL,
    pantry_id  LONG NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (pantry_id) REFERENCES pantries (id)
);