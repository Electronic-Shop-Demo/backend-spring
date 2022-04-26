alter table public.items
    add column if not exists creation_date timestamp default current_timestamp(0) not null,
    add column if not exists statistic     uuid      default gen_random_uuid()    not null,
    alter column id set default gen_random_uuid(),
    alter column image set default gen_random_uuid(),
    alter column rating set default gen_random_uuid(),
    alter column discount set default gen_random_uuid(),
    alter column feedback set default gen_random_uuid();

comment on column items.creation_date is 'Item creation date in iso 8601';
comment on column items.statistic is 'Reference to item statistics (popularity factor and other)';
