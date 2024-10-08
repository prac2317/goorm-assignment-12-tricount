drop table if exists member CASCADE;
 create table member
 (
     user_no   bigint generated by default as identity,
     user_id   varchar(15),
     password  varchar(15),
     nickname  varchar(15),
     primary key (user_no)
 );

 drop table if exists settlement CASCADE;
  create table settlement
  (
      settlement_no   bigint generated by default as identity,
      title   varchar(15),
      primary key (settlement_no)
  );

drop table if exists member_settlement CASCADE;
CREATE TABLE member_settlement (
    user_no BIGINT,
    settlement_no BIGINT,
    PRIMARY KEY (user_no, settlement_no),
    FOREIGN KEY (user_no) REFERENCES member(user_no) ON DELETE CASCADE,
    FOREIGN KEY (settlement_no) REFERENCES settlement(settlement_no) ON DELETE CASCADE
);

 drop table if exists expense CASCADE;
  create table expense
  (
      expense_no   bigint generated by default as identity,
      title   varchar(15),
      user_no BIGINT,
      amount   varchar(15),
      date   varchar(15),
      settlement_no BIGINT,
      FOREIGN KEY (user_no) REFERENCES member(user_no),
      FOREIGN KEY (settlement_no) REFERENCES settlement(settlement_no) ON DELETE CASCADE,
      primary key (expense_no)
  );

--밸런스는 나중에 추가
-- drop table if exists settlement CASCADE;
--  create table settlement
--  (
--      settlement_no   bigint generated by default as identity,
--      title   varchar(15),
--      primary key (settlement_no)
--  );
