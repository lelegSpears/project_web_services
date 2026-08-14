CREATE TABLE tb_order_item
(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INTEGER,
    price      NUMERIC(19, 2),

    PRIMARY KEY (order_id, product_id),

    CONSTRAINT fk_order_item_order
        FOREIGN KEY (order_id)
            REFERENCES tb_order (id),

    CONSTRAINT fk_order_item_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
);