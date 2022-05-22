create table if not exists public.cart
(
    id    uuid                                                         not null default gen_random_uuid()
        constraint favorites_pk
            primary key,
    items uuid[]                                                       not null,
    count smallint generated always as (array_length(items, 1)) stored not null,
    sum   bigint                                                       not null default 0
);

create trigger on_cart_items_trigger
    after update of items
    on public.cart
    for each row
    when (old.items is distinct from new.items)
execute procedure update_cart_sum(new.items);

create or replace function update_cart_sum(new_items uuid[]) returns trigger as
$BODY$
begin
    update public.cart
    set sum = get_cart_items_sum(new_items)
    where *;
end;
$BODY$
    language plpgsql;

create or replace function get_cart_items_sum(new_items uuid[]) returns bigint as
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

create trigger on_items_price_trigger_for_cart
    after update of price
    on public.items
    for each row
    when (old.price is distinct from new.price)
execute procedure update_price_for_cart(new.id, old.price, new.price);

create or replace function update_price_for_cart(product_id uuid, old_price bigint, new_price bigint) returns trigger as
$BODY$
declare
    count_of_updated_items integer;
begin
    select sum(case __id when product_id then 1 else 0 end)
    from unnest(public.cart.items) as dt(__id)
    into count_of_updated_items;

    update public.cart
    set sum = get_cart_items_price(sum, old_price * count_of_updated_items, new_price * count_of_updated_items)
    where product_id = ANY (items);
end;
$BODY$
    language plpgsql;

create or replace function get_cart_items_price(sum bigint, old_price bigint, new_price bigint) returns bigint as
$BODY$
begin
    return sum + (new_price - old_price);
end;
$BODY$
    language plpgsql;
