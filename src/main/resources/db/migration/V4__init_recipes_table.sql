DROP TABLE IF EXISTS recipes;
CREATE TABLE recipes
(
    name        VARCHAR(255) PRIMARY KEY NOT NULL,
    description VARCHAR(100000)          NOT NULL
);