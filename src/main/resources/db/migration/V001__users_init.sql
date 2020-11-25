create table users (
    id serial primary key unique not null,
    username text unique not null,
    password bytea not null,
    salt bytea not null
);
