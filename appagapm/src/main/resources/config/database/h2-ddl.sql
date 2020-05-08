drop sequence if exists seq8_1;
create sequence seq8_1
	increment by 1
	minvalue 	10000000
	maxvalue 	99999999
	start 		10000000
	cache 1
	cycle
;

drop table if exists users;
create table users (
  user_id varchar(64) not null default hash('SHA256', current_timestamp || nextval('seq8_1') ),
  active_yn char(1) not null default 0,
  expired_yn char(1) not null default 0,
  locked_yn char(1) not null default 0,
  pwd_expired_yn char(1) not null default 0,
  login_id varchar(255) unique,
  nickname varchar(64) not null,
  pwd varchar(255) not null,
  reg_ymd varchar(8) not null default to_char(now(), 'YYYYMMDD'),
  reg_hms varchar(6) not null default to_char(now(), 'HH24MISS'),
  reg_ts timestamp not null default current_timestamp,
  upd_ts timestamp not null default current_timestamp,
  constraint users_pk primary key (user_id)
);
comment on table users is '사용자';
comment on column users.user_id is '사용자 고유번호';

drop table if exists user_role;
create table user_role(
  user_id varchar(64) not null ,
  role_id varchar(64) not null ,
  reg_ts timestamp not null default current_timestamp,
  constraint user_role_pk primary key (user_id, role_id)
);
comment on table user_role is '사용자별 부여 권한';

drop table if exists auth_role;
create table auth_role (
  role_id varchar(64) not null default hash('SHA256', current_timestamp || nextval('seq8_1') ),
  role_nm varchar(64) unique,
  role_dp_nm varchar(64) not null,
  role_desc varchar(255),
  reg_ts timestamp not null default current_timestamp,
  constraint auth_role_pk primary key (role_id)
);
comment on table auth_role is '사용자 권한';