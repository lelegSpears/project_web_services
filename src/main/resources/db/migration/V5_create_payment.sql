CREATE TABLE tb_payment
(
    id     BIGINT PRIMARY KEY,
    moment TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (id)
            REFERENCES tb_order (id)
);