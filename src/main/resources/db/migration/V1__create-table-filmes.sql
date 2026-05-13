CREATE TABLE filmes(
    id bigint not null auto_increment,
    titulo varchar(250) Not Null,
    descricao varchar(250),
    categoria varchar(100) not null,

    primary key (id)
);