CREATE TABLE Usuario(
	nome VARCHAR,
	email VARCHAR PRIMARY KEY,
	senha VARCHAR,
	estado VARCHAR
);

CREATE TABLE Trabalho(
	titulo VARCHAR,
	estado VARCHAR,
	cidade VARCHAR,
	valor real,
	horario timestamp,
	data date,
	codigo SERIAL PRIMARY KEY,
	descricao VARCHAR,
	contratante VARCHAR,
	contratado VARCHAR,
	FOREIGN KEY (contratado) REFERENCES Usuario(email),
	FOREIGN KEY (contratante) REFERENCES Usuario(email)
);

CREATE TABLE SOLICITA_TRABALHO(
	emailUsuario VARCHAR,
	codTrabalho int,
	FOREIGN KEY (emailUsuario) REFERENCES Usuario(email),
	FOREIGN KEY (codTrabalho) REFERENCES Trabalho(codigo),
	PRIMARY KEY (emailUsuario,codTrabalho)
);
