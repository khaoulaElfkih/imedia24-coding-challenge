CREATE TABLE products
(
    sku         VARCHAR(16)     NOT NULL
        CONSTRAINT pk_product_id PRIMARY KEY,
    name        VARCHAR(125)    NOT NULL,
    description VARCHAR(125),
    price       DECIMAL           NOT NULL,
    stock       INTEGER         NOT NULL,
    created_at  TIMESTAMP     NOT NULL,
    updated_at  TIMESTAMP     NOT NULL
);
INSERT INTO PRODUCTS (SKU ,CREATED_AT,stock ,DESCRIPTION ,NAME ,PRICE ,UPDATED_AT ) VALUES('12',curdate(),20,'DESCRIPTION ','NAME ',23,curdate());
INSERT INTO PRODUCTS (SKU ,CREATED_AT,stock ,DESCRIPTION ,NAME ,PRICE ,UPDATED_AT ) VALUES('15',curdate(),20,'DESCRIPTION ','NAME ',23,curdate());
