create table usuario (
   id serial not null primary key,
   usuario varchar(100) not null unique,
   senha varchar(100) not null
   );

