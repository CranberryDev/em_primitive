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

create table if not exists test."note"
(
  id   bigserial     not null
    constraint note_id_pk primary key,
  text varchar(2048) not null
);

create table if not exists test."access_level"
(
  value varchar(128) primary key
);
insert into test."access_level" values ('READ');
insert into test."access_level" values ('WRITE');
insert into test."access_level" values ('OWNER');
insert into test."access_level" values ('PUBLIC_READ');
insert into test."access_level" values ('PUBLIC_WRITE');

create table if not exists test."user_note"
(
  id      bigserial not null
    constraint user_note_pk primary key,
  user_id bigint    not null,
  note_id bigint    not null,
  access_level varchar(128) not null,

  constraint user_note_user_fk foreign key (user_id) REFERENCES test."user" (id),
  constraint user_note_note_fk foreign key (note_id) references test."note" (id),
  constraint user_note_access_level_fk foreign key (access_level) references test."access_level" (value),
  constraint user_note_comb_unique UNIQUE (user_id, note_id, access_level)
);

