-- liquibase formatted sql

--changeset ratings:1
--preconditions onFail:MARK_RAN onerror:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM STORE.RATINGS WHERE 1=2
drop table store.ratings cascade constraints;

--changeset ratings:2
create table "STORE"."RATINGS" (
   "PRODUCT_ID" number,
   "USER_ID"    number,
   "RATING"     float
)
logging;
alter table store.ratings
   add constraint ratings_pk primary key ( product_id,
                                           user_id )
      using index
logging;
--rollback DROP TABLE STORE.RATINGS;