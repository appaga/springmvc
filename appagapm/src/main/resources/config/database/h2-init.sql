insert into auth_role (
    role_id
    , role_nm
    , role_dp_nm
    , role_desc
) values 
( '6668bc8ab45c3be7641b3ffbc14dd894a297cfb420eccc69da0f1e93f2dd9b03'
    , 'ROLE_ADMIN', '시스템관리자', '시스템 최고 관리자')
, ('10d586e706cf73c4a533ea58f0bdf88c5346cd81f654368a24b76109e3d9c0fd'
    , 'ROLE_USER', '사용자', '일반사용자')
;

insert into users (
    user_id
    , active_yn
    , login_id
    , nickname
    , pwd
) values
('f06b79c296b30c645c969a60b5599c87bb4be92f0e7cebac907ace544b4c784b'
    , 1, 'admin', '시스템관리자'
    , '$2a$10$2m18CJZqvJxFdsNiOMk/6.tMiTORoT/QFwV2NRVBwAy.mjMUwZbC6')
;

insert into user_role (
    user_id
    , role_id
) values
('f06b79c296b30c645c969a60b5599c87bb4be92f0e7cebac907ace544b4c784b'
    ,'6668bc8ab45c3be7641b3ffbc14dd894a297cfb420eccc69da0f1e93f2dd9b03')
;