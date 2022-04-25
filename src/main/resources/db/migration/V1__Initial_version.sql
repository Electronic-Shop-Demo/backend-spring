create table if not exists public.items
(
    id                  uuid               not null
        constraint items_pk
            primary key,
    title               varchar(44)        not null,
    count               jsonb              not null,
    available_in_points uuid[]             not null,
    price               float(2)           not null,
    image               uuid,
    rating              uuid,
    is_removed          bool default false not null,
    chars               json               not null,
    brand               uuid               not null,
    cat                 uuid               not null,
    discount            uuid,
    feedback            uuid
);

comment on column items.title is 'Item title or just display name';

comment on column items.count is 'Json as product count

[
    {
        "point": "UUID of the point",
        "count": 32
    }
]';

comment on column items.available_in_points is 'References to ids of points which stored in other table "Points"';

comment on column items.image is 'Reference to image id in other database';

comment on column items.rating is 'Storing in other table "Ratings"';

comment on column items.chars is 'Example of json:
[
    {
        "k": "Модель",
        "v": "Patriot PT 412"
    }
]';

comment on column items.discount is 'Reference to discount id specified for this item';

create unique index items_id_uindex
    on items (id);

ALTER TABLE IF EXISTS public.product_entity
    OWNER to "Mairw";
