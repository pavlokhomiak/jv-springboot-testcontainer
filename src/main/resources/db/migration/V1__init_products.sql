create table if not exists product
(
    id bigint auto_increment,
    title varchar(255) not null,
    price decimal not null,
    deleted bool default false,
    constraint products_pk primary key (id)
);
