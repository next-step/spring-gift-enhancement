DROP TABLE products IF EXISTS;
CREATE TABLE products(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      price BIGINT,
                      imageUrl VARCHAR(255));

DROP TABLE users IF EXISTS;
CREATE TABLE users(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                   email VARCHAR(255) UNIQUE,
                   password VARCHAR(255),
                   created_date TIMESTAMP NOT NULL,
                   role VARCHAR(255));

DROP TABLE wishes IF EXISTS;
CREATE TABLE wishes(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
                    quantity BIGINT);

DROP TABLE options IF EXISTS;
CREATE TABLE options(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255),
                     option_value BIGINT);

DROP TABLE product_options IF EXISTS;
CREATE TABLE product_options(product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                             option_id BIGINT NOT NULL REFERENCES options(id) ON DELETE CASCADE,
                             PRIMARY KEY (product_id, option_id));