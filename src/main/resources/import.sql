
create table RULES(
    RULE_ID NUMBER generated as identity
        constraint RULES_PK
            primary key,
    NAME VARCHAR2(28) not null
        unique
);

create table CONDITIONS(
    ID NUMBER generated as identity
        constraint CONDITIONS_PK
            primary key,
    RULES_ID NUMBER not null
        constraint FK_RULE_ID
            references RULES,
    OPERATION VARCHAR2(16) not null,
    VALUE NUMBER not null,
    FEATURE VARCHAR2(16) not null
);

insert into RULES (NAME) values ('fac1');
insert into RULES (NAME) values ('fac2');
insert into RULES (NAME) values ('fac3');

insert into CONDITIONS (rules_id, operation, value, feature) VALUES (1, '>', 90, 'math');
insert into CONDITIONS (rules_id, operation, value, feature) VALUES (1, '>', 95, 'rus');
insert into CONDITIONS (rules_id, operation, value, feature) VALUES (2, '>', 90, 'math');
insert into CONDITIONS (rules_id, operation, value, feature) VALUES (3, '<', 70, 'rus');



