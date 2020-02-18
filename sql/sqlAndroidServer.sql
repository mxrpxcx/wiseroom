CREATE DATABASE dbWiseroom;
USE dbWiseroom;

CREATE TABLE tbOrganizacao(
idOrganizacao int(8) AUTO_INCREMENT,
nome varchar(100),
dominioOrganizacao varchar(100),
CONSTRAINT pkTbOrganizacao PRIMARY KEY (idOrganizacao)

);

CREATE TABLE tbColaborador(

idColaborador int(8) AUTO_INCREMENT, 
nomeColaborador varchar(100), 
idOrganizacao int(8), 
emailColaborador varchar(100), 
administrador boolean,
senhaColaborador varchar(128),
CONSTRAINT pkTbColaborador PRIMARY KEY (idColaborador),
CONSTRAINT fkTbColaborador FOREIGN KEY (idOrganizacao) REFERENCES tbOrganizacao(idOrganizacao) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE tbSala(

idSala int(8) AUTO_INCREMENT, 
idOrganizacao int(8), 
nomeSala varchar(100), 
capacidadeSala int(4), 
areaSala float, 
descricaoSala varchar(500),

CONSTRAINT pkTbSala PRIMARY KEY (idSala),
CONSTRAINT fkTbSala FOREIGN KEY (idOrganizacao) REFERENCES tbOrganizacao(idOrganizacao) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE tbReserva(

idReserva int(8) AUTO_INCREMENT, 
descricaoReserva varchar(500), 
dataReserva varchar(12), 
nomeColaborador varchar(100),
horaInicioReserva varchar(6),
horaFimReserva varchar(6),
idColaboradorReserva int(8), 
idSalaReserva int(8),

CONSTRAINT pkTbReserva PRIMARY KEY (idReserva),
CONSTRAINT fkTbReservaY FOREIGN KEY (idColaboradorReserva) REFERENCES tbColaborador(idColaborador) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fkTbReservaZ FOREIGN KEY (idSalaReserva) REFERENCES tbSala(idSala) ON DELETE CASCADE ON UPDATE CASCADE

);

SELECT * FROM tbReserva INNER JOIN tbsala ON tbReserva.idSalaReserva = tbSala.idSala WHERE tbReserva.idSalaReserva = 14;


INSERT INTO tbOrganizacao VALUES (1, "ORG", "b");
INSERT INTO tbSala VALUES (14, 1, "SALA 1", 50, 40.4, "DESCRICAO");
INSERT INTO tbReserva VALUES (4, "RESERVA 1", "10-02-2020", "b", "10:40", "12:50", 2, 14);
INSERT INTO tbReserva VALUES (3, "RESERVA 2", "10-02-2020", "b", "10:40", "12:50", 1, 14);



