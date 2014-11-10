create table user_attempts (
	id int(11) not null auto_increment,
	username varchar(45) not null,
	attempts int(11)  not null,
	lastModified datetime not null,
	primary key (id)
);