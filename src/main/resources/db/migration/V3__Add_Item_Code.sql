create sequence if not exists item_code_sequence;

alter table public.items
    add column if not exists code integer not null default nextval('item_code_sequence');

alter sequence item_code_sequence owned by public.items.code;

comment on column items.code is 'Item unique code';
