insert into accounts (id, document_number)
VALUES ('account1', 'document_number1');
insert into accounts (id, document_number)
VALUES ('account2', 'document_number2');
insert into accounts (id, document_number)
VALUES ('account3', 'document_number3');

INSERT INTO transactions (accountId, operationTypeId, amount, eventDate)
VALUES ('account1', 1, -50.0, '2020-01-01T10:32:07.7199222');
INSERT INTO transactions (accountId, operationTypeId, amount, eventDate)
VALUES ('account1', 1, -23.5, '2020-01-01T10:48:12.2135875');
INSERT INTO transactions (accountId, operationTypeId, amount, eventDate)
VALUES ('account1', 1, -18.7, '2020-01-02T19:01:23.1458543');
INSERT INTO transactions (accountId, operationTypeId, amount, eventDate)
VALUES ('account3', 4, 60.0, '2020-01-05T09:34:18.5893223');