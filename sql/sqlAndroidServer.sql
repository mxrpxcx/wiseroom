CREATE DATABASE WiseRoomDb;
USE WiseRoomDb;

CREATE TABLE tbColaborador(

idColaborador int(8), 
nome varchar(100), 
idOrganizacao int(8), 
email varchar(100), 
administrador boolean,
senha varchar(128),
CONSTRAINT pkTbColaborador PRIMARY KEY (id)

);

CREATE TABLE tbSala(

idSala int(8), 
nome varchar(100), 
capacidade int(4), 
areaDaSala float, 
descricaoSala varchar(500),

CONSTRAINT pkTbSala PRIMARY KEY (id)

);

CREATE TABLE tbReserva(

idReserva int(8), 
descricaoReserva varchar(500), 
dataData varchar(12), 
horaInicio varchar(6),
horaFim varchar(6),
idColaboradorReserva int(8), 
idSalaReserva int(8),

CONSTRAINT pkTbReserva PRIMARY KEY (idReserva),
CONSTRAINT fkTbReservaY FOREIGN KEY (idColaboradorReserva) REFERENCES tbColaborador(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fkTbReservaZ FOREIGN KEY (idSalaReserva) REFERENCES tbSala(id) ON DELETE CASCADE ON UPDATE CASCADE
);








