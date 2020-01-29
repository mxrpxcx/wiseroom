CREATE DATABASE WiseRoomDb;
USE WiseRoomDb;

CREATE TABLE tbColaborador(

id int, 
nome varchar(50), 
idOrganizacao int, 
email varchar(100), 
administrador boolean,
senha varchar(100),
CONSTRAINT pkTbColaborador PRIMARY KEY (id)

);

CREATE TABLE tbData(

id int,
nomeData varchar(50), 
dataData varchar(50), 
horaData varchar(50),

CONSTRAINT pkTbColaborador PRIMARY KEY (id)


);

CREATE TABLE tbSala(

id int, 
nome varchar(50), 
capacidade int, 
areaDaSala float, 
descricaoSala varchar(500),

CONSTRAINT pkTbSala PRIMARY KEY (id)

);

CREATE TABLE tbReserva(

idReserva int, 
idDataReservada int,
idColaboradorReserva int, 
idSalaReserva int,

CONSTRAINT pkTbReserva PRIMARY KEY (idReserva),
CONSTRAINT fkTbReservaX FOREIGN KEY (idDataReservada) REFERENCES tbData(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fkTbReservaY FOREIGN KEY (idColaboradorReserva) REFERENCES tbColaborador(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fkTbReservaZ FOREIGN KEY (idSalaReserva) REFERENCES tbSala(id) ON DELETE CASCADE ON UPDATE CASCADE

);





