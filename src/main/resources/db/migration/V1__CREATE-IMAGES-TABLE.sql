create table images (
    id serial primary key not null,
    image bytea not null,
    created_at timestamp not null
);