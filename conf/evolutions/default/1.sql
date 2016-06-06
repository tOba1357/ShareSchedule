# --- !Ups

create table tblremarks (
  id                            integer auto_increment not null,
  description                   varchar(255),
  date                          date,
  constraint pk_tblremarks primary key (id)
);

create table tblschedule (
  id                            integer auto_increment not null,
  user_id                       integer,
  date                          date,
  term_id                       integer,
  free                          tinyint(1) default 0,
  constraint pk_tblschedule primary key (id)
);

create table tblterm (
  id                            integer auto_increment not null,
  description                   varchar(255),
  constraint pk_tblterm primary key (id)
);

create table tbluser (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_tbluser primary key (id)
);

alter table tblremarks add constraint fk_tblremarks_id foreign key (id) references tbluser (id) on delete restrict on update restrict;
create index ix_tblremarks_id on tblremarks (id);

alter table tblschedule add constraint fk_tblschedule_user_id foreign key (user_id) references tbluser (id) on delete restrict on update restrict;
create index ix_tblschedule_user_id on tblschedule (user_id);

alter table tblschedule add constraint fk_tblschedule_term_id foreign key (term_id) references tblterm (id) on delete restrict on update restrict;
create index ix_tblschedule_term_id on tblschedule (term_id);


# --- !Downs

alter table tblremarks drop foreign key fk_tblremarks_id;
drop index ix_tblremarks_id on tblremarks;

alter table tblschedule drop foreign key fk_tblschedule_user_id;
drop index ix_tblschedule_user_id on tblschedule;

alter table tblschedule drop foreign key fk_tblschedule_term_id;
drop index ix_tblschedule_term_id on tblschedule;

drop table if exists tblremarks;

drop table if exists tblschedule;

drop table if exists tblterm;

drop table if exists tbluser;

