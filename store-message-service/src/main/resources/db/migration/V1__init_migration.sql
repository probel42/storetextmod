create table messages (
	id		integer primary key generated always as identity,
	uuid	uuid not null,
	text	varchar(256) not null
);
