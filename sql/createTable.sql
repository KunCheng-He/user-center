drop table if exists user;

create table user
(
    id bigint auto_increment not null primary key comment 'user id',
    name varchar(256) null comment 'user name',
    password varchar(256) null comment 'user password',
    tel_phone varchar(128) null comment 'user telephone',
    email varchar(128) null comment 'user email',
    avatar varchar(1024) null comment 'user avatar',
    gender tinyint null comment 'user gender: 0-male, 1-female',
    status int default 0 null comment 'user status: 0-normal',
    create_time datetime default current_timestamp null comment 'create time',
    update_time datetime default current_timestamp null on update current_timestamp comment 'update time',
    is_deleted tinyint default 0 not null comment 'is deleted: 0-not deleted'
)