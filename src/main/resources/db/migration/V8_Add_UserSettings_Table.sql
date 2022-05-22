create table if not exists public.user_settings
(
    id            uuid    not null default gen_random_uuid()
        constraint favorites_pk
            primary key,
    notifications boolean not null default false,
    discounts     boolean not null default false
);
