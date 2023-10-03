create table person (
    id          serial          not null   primary key,
    login       varchar(64)     not null   unique,
    password    varchar(128)    not null
);

comment on table person is 'Пользователи';
comment on column person.login is 'Логин';
comment on column person.password is 'Пароль';