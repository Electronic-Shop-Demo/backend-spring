create sequence if not exists order_code_sequence;
create type order_status as enum ('DONE', 'AWAITING_RECEIVE', 'AWAITING_PAYMENT', 'CREATED', 'CANCELED', 'REMOVED');
create type receive_type as enum ('DELIVERY', 'PICKUP');

create table if not exists public.order
(
    id                uuid                                                         not null default gen_random_uuid()
        constraint favorites_pk
            primary key,
    items             uuid[]                                                       not null,
    code              integer                                                      not null default nextval('order_code_sequence'),
    status            order_status                                                 not null default 'CREATED',
    status_updated_at timestamp                                                    not null default current_timestamp(0),
    count             smallint generated always as (array_length(items, 1)) stored not null,
    sum               bigint                                                       not null default 0,
    created_at        timestamp                                                    not null default current_timestamp(0),
    phone             varchar(14)                                                  not null,
    receive_type      receive_type                                                 not null,
    receive_value     varchar(140)                                                 not null
);

alter sequence order_code_sequence owned by public.order.code;

create trigger on_order_status_trigger
    after update of status
    on public.order
    for each row
    when (old.status is distinct from new.status)
execute procedure update_order_status_updated_at();

create or replace function update_order_status_updated_at() returns trigger as
$BODY$
begin
    new.status_updated_at = current_timestamp(0);
    return new;
end;
$BODY$
    language plpgsql;

create trigger on_order_items_trigger
    after update of items
    on public.order
    for each row
    when (old.items is distinct from new.items)
execute procedure update_order_sum(new.items);

create or replace function update_order_sum(new_items uuid[]) returns trigger as
$BODY$
begin
    update public.order
    set sum = get_order_items_sum(new_items)
    where *;
end;
$BODY$
    language plpgsql;

create or replace function get_order_items_sum(new_items uuid[]) returns bigint as
$BODY$
declare
    table_record record;
    total        bigint = 0;
begin
    for i in 1 .. array_upper(new_items, 1)
        loop
            for table_record in select price from items where items.id = new_items[i]
                loop
                    total = total + table_record.price;
                end loop;
        end loop;

    return total;
end;
$BODY$
    language plpgsql;
