create schema if NOT EXISTS test;

create table if not exists test."user"
(
  id         bigserial not null
    constraint test_id_pk
      primary key,
  name       varchar(128),
  state      varchar(128),
  number     integer,
  money      double precision,
  is_applied boolean,
  date       date
);