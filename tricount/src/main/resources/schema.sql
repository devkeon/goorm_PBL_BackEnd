drop table if exists expense;
drop table if exists member_settlement;
drop table if exists settlement;
drop table if exists member;

create table member (
    id bigint generated by default as identity,
    login_id varchar(255),
    password varchar(255),
    name varchar(255),
    primary key (id),
    unique (login_id)
);

create table settlement (
        id bigint generated by default as identity,
        name varchar(255),
        primary key (id)
);

create table member_settlement(
        id bigint generated by default as identity,
        settlement_id bigint,
        member_id bigint,
        primary key (id),
        foreign key (settlement_id) references settlement (id),
        foreign key (member_id) references member (id)
);

create table expense (
    id bigint generated by default as identity,
    name varchar(255),
    settlement_id bigint,
    member_id bigint,
    price bigint,
    expense_date_time datetime,
    primary key (id),
    foreign key (settlement_id) references settlement (id),
    foreign key (member_id) references member (id)
);