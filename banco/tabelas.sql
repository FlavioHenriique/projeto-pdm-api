CREATE TABLE Usuario(
	nome VARCHAR,
	email VARCHAR PRIMARY KEY,
	senha VARCHAR,
	estado VARCHAR,
	cidade VARCHAR
);

CREATE TABLE Trabalho(
	titulo VARCHAR,
	estado VARCHAR,
	cidade VARCHAR,
	momentoCriacao timestamp,
	valor real,
	horario varchar,
	data date,
	codigo SERIAL PRIMARY KEY,
	descricao VARCHAR,
	contratante VARCHAR,
	contratado VARCHAR,
	categoria varchar,
	FOREIGN KEY (contratado) REFERENCES Usuario(email)
	ON DELETE CASCADE,
	FOREIGN KEY (contratante) REFERENCES Usuario(email)
	ON DELETE CASCADE
);

CREATE TABLE SOLICITA_TRABALHO(
	emailUsuario VARCHAR,
	codTrabalho int,
	estado varchar,
	FOREIGN KEY (emailUsuario) REFERENCES Usuario(email)
	ON DELETE CASCADE,
	FOREIGN KEY (codTrabalho) REFERENCES Trabalho(codigo)
	ON DELETE CASCADE,
	PRIMARY KEY (emailUsuario,codTrabalho)
);

insert into usuario (nome,email,senha,cidade,estado) values ('Flavio','flavio@gmail.com',
'328838659689916417705350303073944936763','sousa','pb');
insert into usuario (nome,email,senha,cidade,estado) values ('Ari','ari@gmail.com',
'335179228355690360138234256155070591937','sousa','pb');

insert into trabalho(titulo,estado,cidade,valor,horario,data,descricao,contratante,categoria,momentocriacao)
values ('Trabalho 1', 'PB', 'Sousa', 20,'10:30','2018-02-05','DKOSFKDO FKODFKDO FKDO','flavio@gmail.com','Construção',current_timestamp);


