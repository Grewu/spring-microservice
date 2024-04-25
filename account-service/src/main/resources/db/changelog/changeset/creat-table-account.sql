create table if not exists account
(
    id            bigint primary key not null,
    name          varchar(255)       not null,
    email         varchar(255)       not null,
    phone_number  varchar(255)       not null,
    creation_date timestamp          not null
);