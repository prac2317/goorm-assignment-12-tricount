drop table if exists member CASCADE;
 create table member
 (
     user_no   bigint generated by default as identity,
     user_id   varchar(15),
     password  varchar(15),
     nickname  varchar(15),
     primary key (user_no)
 );