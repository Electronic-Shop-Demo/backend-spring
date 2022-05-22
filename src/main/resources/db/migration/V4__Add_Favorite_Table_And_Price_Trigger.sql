create table if not exists public.favorites
(
    id    uuid                                                         not null default gen_random_uuid()
        constraint favorites_pk
            primary key,
    items uuid[] not null,
    count smallint generated always as (array_length(items, 1)) stored not null,
    sum   bigint                                                       not null default 0
);

create trigger on_favorite_items_trigger
    after update of items
    on public.favorites
    for each row
    when (old.items is distinct from new.items)
execute procedure update_sum(new.items);

create or replace function update_sum(new_items uuid[]) returns trigger as
$BODY$
begin
    update public.favorites
    set sum = get_items_sum(new_items)
    where *;
end;
$BODY$
    language plpgsql;

create or replace function get_items_sum(new_items uuid[]) returns bigint as
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

create trigger on_items_price_trigger
    after update of price
    on public.items
    for each row
    when (old.price is distinct from new.price)
execute procedure update_price_for_lists(new.id, old.price, new.price);

create or replace function update_price_for_lists(product_id uuid, old_price bigint, new_price bigint) returns trigger as
$BODY$
begin
    update public.favorites
    set sum = get_items_price(sum, old_price, new_price)
    where product_id = ANY (items);
end;
$BODY$
    language plpgsql;

create or replace function get_items_price(sum bigint, old_price bigint, new_price bigint) returns bigint as
$BODY$
begin
    return sum + (new_price - old_price);
end;
$BODY$
    language plpgsql;
