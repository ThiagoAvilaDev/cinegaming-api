CREATE TABLE jogos(
    id bigint not null auto_increment,
    titulo varchar(250) Not Null,
    ano_lancamento varchar(4),
    descricao varchar(1000),
    categoria_jogo varchar(100) not null,
    ativo tinyint,


    primary key (id)
);