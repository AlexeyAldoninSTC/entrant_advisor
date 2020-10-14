create table RULES
(
    RULE_ID INTEGER auto_increment primary key,
    NAME VARCHAR (28) not null
        unique
);

create table CONDITIONS
(
    ID NUMBER generated as identity
        constraint CONDITIONS_PK
        primary key,
    RULE_ID INTEGER not null
        constraint FK_RULE_ID
        references RULES,
    OPERATION VARCHAR2 (16) not null,
    VALUE NUMBER not null,
    FEATURE VARCHAR2 (16) not null
);

insert into RULES (NAME)
values ('fac1');
insert into RULES (NAME)
values ('fac2');
insert into RULES (NAME)
values ('fac3');

insert into CONDITIONS (rule_id, operation, value, feature)
VALUES (1, '>', 90, 'math');
insert into CONDITIONS (rule_id, operation, value, feature)
VALUES (1, '>', 95, 'rus');
insert into CONDITIONS (rule_id, operation, value, feature)
VALUES (2, '>', 90, 'math');
insert into CONDITIONS (rule_id, operation, value, feature)
VALUES (3, '<', 70, 'rus');



