CREATE TABLE product
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    price           INT          NOT NULL,
    imageUrl        VARCHAR(255) NOT NULL,
    needsMdApproval BOOLEAN      NOT NULL
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,

    CONSTRAINT fk_wishlist_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT uc_member_product UNIQUE (member_id, product_id)
);

CREATE TABLE option
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT      NOT NULL,
    name       VARCHAR(50) NOT NULL,
    quantity   BIGINT      NOT NULL,

    CONSTRAINT chk_quantity CHECK (quantity >= 1 AND quantity < 100000000),
    CONSTRAINT fk_option_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT uc_product_option UNIQUE (product_id, name)
);
