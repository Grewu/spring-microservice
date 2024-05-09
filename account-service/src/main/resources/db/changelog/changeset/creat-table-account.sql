create table if not exists account
(
    id            serial primary key not null,
    name          varchar(255)       not null,
    email         varchar(255)       not null,
    phone_number  varchar(255)       not null,
    creation_date timestamp          not null
);
create table accounts_bills
(
    account_id bigint not null,
    bill_id    bigint not null
);