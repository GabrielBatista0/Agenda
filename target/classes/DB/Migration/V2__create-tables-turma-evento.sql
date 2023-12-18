create table turma (
   id serial not null primary key,
   nome varchar(100) not null unique,
   turno varchar(30) not null,
   ativo boolean not null
   );



create table evento (
    id serial not null primary key,
    titulo varchar(150) not null,
    local varchar(150) not null,
    instrutor varchar(100) not null,
    descricao varchar(200) not null,
    data_inicio timestamp not null,
    data_fim timestamp not null,
    id_turma serial not null,
    constraint fk_evento_turma foreign key (id_turma) references turma(id)
)