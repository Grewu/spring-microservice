create table if not exists bill
(
    bill_id            serial primary key not null,
    account_id        bigint         not null,
    amount            numeric(19, 2) not null,
    is_default        boolean        not null,
    creation_date     timestamp      not null,
    overdraft_enabled boolean        not null
);
