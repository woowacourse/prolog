drop table department;
drop table department_member;

CREATE TABLE IF NOT EXISTS department
(
    id   bigint auto_increment primary key,
    part varchar(50) not null,
    term varchar(50) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table if not exists department_member
(
    id        bigint auto_increment primary key,
    member_id bigint not null,
    department_id  bigint not null,
    constraint FK_DEPARTMENT_MEMBER_ON_MEMBER
        foreign key (member_id) references prolog.member (id),
    constraint FK_DEPARTMENT_MEMBER_ON_DEPARTMENT
        foreign key (department_id) references prolog.department (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

insert into department(id, part, term) values (1, 'BACKEND', 'THIRD');
insert into department(id, part, term) values (2, 'FRONTEND', 'THIRD');
insert into department(id, part, term) values (3, 'BACKEND', 'FOURTH');
insert into department(id, part, term) values (4, 'FRONTEND', 'FOURTH');
insert into department(id, part, term) values (5, 'BACKEND', 'FIFTH');
insert into department(id, part, term) values (6, 'FRONTEND', 'FIFTH');
insert into department(id, part, term) values (7, 'ANDROID', 'FIFTH');

insert into department_member (id, member_id, department_id)
    (select id, member_id, group_id from group_member);
