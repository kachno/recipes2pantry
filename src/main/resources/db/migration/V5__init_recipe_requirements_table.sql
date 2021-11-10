DROP TABLE IF EXISTS recipe_requirements;
CREATE TABLE recipe_requirements
(
    id         LONG PRIMARY KEY AUTO_INCREMENT,
    quantity   INT  NOT NULL,
    product_id LONG NOT NULL,
    recipe_id  LONG NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (recipe_id) REFERENCES recipes (name)
);