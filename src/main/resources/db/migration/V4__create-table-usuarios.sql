CREATE TABLE usuarios(
    id bigint not null auto_increment,
    nome_completo varchar(100) Not Null,
    email varchar(100) unique not null,
    senha varchar(255) not null,
    ativo BOOLEAN not null,

    primary key (id)
);