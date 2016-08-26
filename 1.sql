
create user app with password 'app';

create database conversations;

grant all privileges on database conversations to app;

\c conversations

create table conversation(
  id serial primary key,
  creator_id	int,
  create_at	 timestamp with time zone	DEFAULT	NULL,
  message_count	int);

create table users(
  id serial primary key,
  username text,
  full_name text,
  age int);

CREATE	TABLE	 messages	(
 id	serial primary key,
 text	text,
 created_at	timestamp with time zone	DEFAULT	NULL,
 sender int references users(id) on update CASCADE,
 recipient	int references users(id) on update CASCADE,
 conversation_id 	int references conversation(id) on update CASCADE);

alter table conversation owner to app;
alter table messages owner to app;
alter table users owner to app;

insert into users(username, full_name, age) values('jtrudeau', 'Justin Trudeau', 41);
insert into users(username, full_name, age) values('amerkel', 'Angela Dorothea Merkel', 62);
insert into users(username, full_name, age) values('xjinping', 'Xi Jinping', 63);
insert into users(username, full_name, age) values('vputin', 'Vladimir Vladimirovich Putin', 63);
insert into users(username, full_name, age) values('bobama', 'Barack Hussein Obama', 55);

insert into conversation(creator_id, create_at, message_count) values(1, CURRENT_TIMESTAMP, 0);
insert into conversation(creator_id, create_at, message_count) values(2, CURRENT_TIMESTAMP, 0);

insert into messages(text, created_at, sender, recipient, conversation_id) values('hey!', CURRENT_TIMESTAMP, 1, 3, 1);

create table conversation(
  id serial primary key,
  creator_id	int,
  create_at	 timestamp with time zone	DEFAULT	NULL,
  message_count	int);

create table users(
  id serial primary key,
  username text,
  full_name text,
  age int);

CREATE	TABLE	 messages	(
 id	serial primary key,
 text	text,
 created_at	timestamp with time zone	DEFAULT	NULL,
 sender int references users(id) on update CASCADE,
 recipient	int references users(id) on update CASCADE,
 conversation_id 	int references conversation(id) on update CASCADE);

insert into users(username, full_name, age) values('jtrudeau', 'Justin Trudeau', 41);
insert into users(username, full_name, age) values('amerkel', 'Angela Dorothea Merkel', 62);
insert into users(username, full_name, age) values('xjinping', 'Xi Jinping', 63);
insert into users(username, full_name, age) values('vputin', 'Vladimir Vladimirovich Putin', 63);
insert into users(username, full_name, age) values('bobama', 'Barack Hussein Obama', 55);

insert into conversation(creator_id, create_at, message_count) values(1, CURRENT_TIMESTAMP, 0);
insert into conversation(creator_id, create_at, message_count) values(2, CURRENT_TIMESTAMP, 0);

insert into messages(text, created_at, sender, recipient, conversation_id) values('hey!', CURRENT_TIMESTAMP, 1, 3, 1);