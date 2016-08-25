
create user app;

create database user_conversations;

grant all privileges on database user_conversations to app;

create table conversation(
  id serial primary key,
  creator_id	bigint,
  create_at	 timestamp with time zone	DEFAULT	NULL,
  message_count	bigint);

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
