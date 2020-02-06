CREATE DATABASE dbWiseroom;
USE dbWiseroom;

CREATE TABLE tbOrganizacao(
idOrganizacao int(8),
nome varchar(100),
dominioOrganizacao varchar(100),
CONSTRAINT pkTbOrganizacao PRIMARY KEY (idOrganizacao)

);

CREATE TABLE tbColaborador(

idColaborador int(8), 
nome varchar(100), 
idOrganizacao int(8), 
email varchar(100), 
administrador boolean,
senha varchar(128),
CONSTRAINT pkTbColaborador PRIMARY KEY (idColaborador),
CONSTRAINT fkTbColaborador FOREIGN KEY (idOrganizacao) REFERENCES tbOrganizacao(idOrganizacao) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE tbSala(

idSala int(8), 
idOrganizacao int(8), 
nome varchar(100), 
capacidade int(4), 
areaDaSala float, 
descricaoSala varchar(500),

CONSTRAINT pkTbSala PRIMARY KEY (idSala),
CONSTRAINT fkTbSala FOREIGN KEY (idOrganizacao) REFERENCES tbOrganizacao(idOrganizacao) ON DELETE CASCADE ON UPDATE CASCADE

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








