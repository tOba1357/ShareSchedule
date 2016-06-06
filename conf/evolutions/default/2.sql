# --- !Ups

ALTER TABLE tblschedule CHANGE free state INTEGER;

# --- !Downs

ALTER TABLE tblschedule CHANGE state free tinyint(1) default 0;
