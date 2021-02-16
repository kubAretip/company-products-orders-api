insert into status(id, name, locale, title, description)
values (1, 'CREATED', 'pl', 'Zamówienie utworzone',
        'Zamówienie zostało utworzone i oczekuje na zaakceptowanie przez dział zamówień.');

insert into status(id, name, locale, title, description)
values (2, 'CREATED', 'en', 'Order created',
        'The order was created and now is waiting for accept by the orders department.');

insert into status(id, name, locale, title, description)
values (3, 'ACCEPTED', 'pl', 'Zamówienie zaakceptowane', 'Zamówienie zostało zaakceptowane i wysłane realizacji.');

insert into status(id, name, locale, title, description)
values (4, 'ACCEPTED', 'en', 'Order accepted', 'The order was accepted and sent to processing.');

insert into status(id, name, locale, title, description)
values (5, 'REJECTED', 'pl', 'Zamówienie odrzucone', 'Zamówienie zostało odrzucone przez dział zamówień.');

insert into status(id, name, locale, title, description)
values (6, 'REJECTED', 'en', 'Order rejected', 'The order was rejected by orders department.');
