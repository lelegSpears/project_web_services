alter table product
add column stock_quantity INTEGER not null default 0
check (stock_quantity between 0 and 10000);