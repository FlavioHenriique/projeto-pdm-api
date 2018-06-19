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
