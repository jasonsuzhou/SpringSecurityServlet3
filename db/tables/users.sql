create table users(
	username varchar(45) not null,
	password varchar(45) not null,
	enabled tinyint not null default 1,
	primary key(username)
);
alter table users add accountNonExpired TINYINT NOT NULL DEFAULT 1; 
alter table users add accountNonLocked TINYINT NOT NULL DEFAULT 1;
alter table users add credentialsNonExpired TINYINT NOT NULL DEFAULT 1;