alter table tb_users
add column role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER';