create table user_roles(
	user_role_id int(11) not null auto_increment,
	username varchar(45) not null,
	role varchar(45) not null,
	primary key(user_role_id),
	unique key uni_username_role (role,username),
	key fk_username_idx (username),
	constraint fk_username foreign key (username) references users (username)
);