DROP TABLE IF EXISTS tbl_invoice_items;

CREATE TABLE tbl_invoice_items (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  quantity DOUBLE,
  price DOUBLE,
  product_id BIGINT,
  invoice_id BIGINT
);


DROP TABLE IF EXISTS tlb_invoices;

CREATE TABLE tlb_invoices (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  number_invoice VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  customer_id BIGINT,
  create_at TIMESTAMP,
  state VARCHAR(250) NOT NULL

);