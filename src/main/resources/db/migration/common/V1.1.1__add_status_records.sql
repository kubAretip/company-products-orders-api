insert into status(name, locale, title, description)
values ('CREATED', 'en', 'Order created',
        'The order was created and now is waiting for accept by the orders department.');

insert into status(name, locale, title, description)
values ('ACCEPTED', 'en', 'Order accepted', 'The order was accepted and sent to processing.');

insert into status(name, locale, title, description)
values ('CREATED', 'pl', 'Zamówienie utworzone',
        'Zamówienie zostało utworzone i oczekuje na zaakceptowanie przez dział zamówień.');

insert into status(name, locale, title, description)
values ('ACCEPTED', 'pl', 'Zamówienie zaakceptowane', 'Zamówienie zostało zaakceptowane i wysłane realizacji.');