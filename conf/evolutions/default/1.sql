# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tblremarks (
  id                            integer auto_increment not null,
  description                   varchar(255),
  date                          datetime(6),
  constraint pk_tblremarks primary key (id)
);

create table tblschedule (
  id                            integer auto_increment not null,
  date                          datetime(6),
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

alter table tblschedule add constraint fk_tblschedule_id foreign key (id) references tblterm (id) on delete restrict on update restrict;
create index ix_tblschedule_id on tblschedule (id);


# --- !Downs

alter table tblremarks drop foreign key fk_tblremarks_id;
drop index ix_tblremarks_id on tblremarks;

alter table tblschedule drop foreign key fk_tblschedule_id;
drop index ix_tblschedule_id on tblschedule;

drop table if exists tblremarks;

drop table if exists tblschedule;

drop table if exists tblterm;

drop table if exists tbluser;

