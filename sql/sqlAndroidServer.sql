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
horaInicioReserva varchar(6),
horaFimReserva varchar(6),
idColaboradorReserva int(8), 
idSalaReserva int(8),

CONSTRAINT pkTbReserva PRIMARY KEY (idReserva),
CONSTRAINT fkTbReservaY FOREIGN KEY (idColaboradorReserva) REFERENCES tbColaborador(idColaborador) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fkTbReservaZ FOREIGN KEY (idSalaReserva) REFERENCES tbSala(idSala) ON DELETE CASCADE ON UPDATE CASCADE

);








