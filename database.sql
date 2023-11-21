select * from categories;

create table products
(
    id BIGINT not null auto_increment,
    name varchar(100) not null ,
    price bigint not null ,
    category_id bigint not null ,
    primary key (id),
    foreign key fk_products_categories(category_id) references categories(id)
)engine InnoDB;

select * from products;

alter table categories
    add column created_date timestamp;

alter table categories
    add column last_modified_date timestamp;