create table labels
(
    id      bigserial not null,
    name    VARCHAR,
    post_id BIGINT,
    primary key (id)
);

create table posts
(
    id          bigserial not null,
    content     VARCHAR,
    create_time TIMESTAMP not null,
    status      VARCHAR   not null,
    update_time TIMESTAMP,
    writer_id   BIGINT,
    primary key (id)
);

create table writers
(
    id         bigserial not null,
    first_name VARCHAR   not null,
    last_name  VARCHAR   not null,
    primary key (id)
);

alter table labels
    add constraint post_id_fk foreign key (post_id) references posts on delete cascade;

alter table posts
    add constraint writer_id_fk foreign key (writer_id) references writers on delete cascade;
