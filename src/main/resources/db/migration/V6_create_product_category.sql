CREATE TABLE tb_product_category
(
    product_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    PRIMARY KEY (product_id, category_id),

    CONSTRAINT fk_product_category_product
        FOREIGN KEY (product_id)
            REFERENCES product (id),

    CONSTRAINT fk_product_category_category
        FOREIGN KEY (category_id)
            REFERENCES tb_category (id)
);