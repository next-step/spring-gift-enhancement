insert into product (name, price, image_url)
values ('사과 2kg', 14900, 'https://example1.png');

insert into product (name, price, image_url)
values ('카카오프랜즈 인형', 30000, 'https://example2.png');

insert into member (email, password, role)
values ('admin@example.com', '1234', 'ADMIN');

insert into member (email, password, role)
values ('user@example.com', '0000', 'USER');

insert into wish (member_id, product_id)
values (2, 1);

insert into member (email, password, role)
values ('user2@example.com', '0000', 'USER');

insert into wish (member_id, product_id)
values (3, 2);