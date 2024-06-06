CREATE DATABASE university_lib;

USE university_lib;

CREATE TABLE ALUNO(
   ra NUMERIC(14) UNIQUE PRIMARY KEY,
   nome varchar(200) NOT NULL,
   email varchar(50) NOT NULL
   );

CREATE TABLE EXEMPLAR(
   codigo NUMERIC(10) UNIQUE PRIMARY KEY,
   nome varchar(100),
   qtd_paginas integer(10)
   );

CREATE TABLE LIVRO(
   codigo NUMERIC(10) UNIQUE,
   isbn varchar(40),
   edicao integer(10),
   PRIMARY KEY(codigo),
   FOREIGN KEY(codigo) REFERENCES EXEMPLAR(codigo)
   );

CREATE TABLE REVISTA(
   codigo NUMERIC(10) UNIQUE,
   issn varchar(40),
   PRIMARY KEY(codigo),
   FOREIGN KEY(codigo) REFERENCES EXEMPLAR(codigo)
   );

/*CREATE TABLE TipoExemplar(TipoExemplarID int primary key, TipoExemplar varchar(10));*/

CREATE TABLE ALUGUEL(
   codigo NUMERIC(10) UNIQUE NOT NULL,
   ra NUMERIC(14) UNIQUE NOT NULL,
   data_retirada DATE NOT NULL,
   data_devolucao DATE,
   PRIMARY KEY(codigo, ra, data_retirada),
   FOREIGN KEY(codigo) REFERENCES EXEMPLAR(codigo),
   FOREIGN KEY(ra) REFERENCES ALUNO(ra)
   );

/*Insercao de Alunos e Livros*/

INSERT INTO ALUNO (ra, nome, email) VALUES

(101,"Gabriel", "gabriel.santos@gmail.com"),
(102,"Clara", "clara.carvalho@gmail.com"),
(103,"Pedro", "pedro.henrique@gmail.com"),
(104,"Leandro", "leandro.colevati@gmail.com");

INSERT INTO EXEMPLAR (codigo, nome, qtd_paginas) VALUES

(1001, "Harry Potter: E a Camera", 231),
(1002, "Analise e Projeto de Software", 421),
(1003, "Programacao e Orienta??o a Objetos", 854),
(1004, "Administracao Geral", 231),
(1005, "Veja Geral", 12),
(1006, "Globo", 16);

INSERT INTO LIVRO (codigo, isbn, edicao) VALUES

(1001, "954-214-35-2", 3),
(1002, "652-134-96-7", 6),
(1003, "025-454-32-4", 9),
(1004, "215-384-99-0", 5);

INSERT INTO REVISTA (codigo, issn) VALUES

(1005, "95-974-4"),
(1006, "21-751-0");

INSERT INTO ALUGUEL (codigo, ra, data_retirada, data_devolucao) VALUES

(1001, 102, "2024-06-01", "2024-09-01");