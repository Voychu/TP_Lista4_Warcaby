CREATE DATABASE IF NOT EXISTS `checkers_games`;


CREATE TABLE Typy_gry
(
	ID_typu int NOT NULL AUTO_INCREMENT,
	nazwa_typu varchar(30) NOT NULL,
	PRIMARY KEY (ID_typu)
)AUTO_INCREMENT = 1;


INSERT INTO Typy_gry(nazwa_typu)
VALUES('classic checkers');



CREATE TABLE Gry
(
	ID_gry int NOT NULL AUTO_INCREMENT,
	typ_gry int NOT NULL,
	PRIMARY KEY (ID_gry),
	FOREIGN KEY (typ_gry) REFERENCES Typy_gry(ID_typu)
);	


ALTER TABLE Gry ADD COLUMN time_of_starting DATETIME AFTER typ_gry;


CREATE TABLE Ruchy
(
	ID_gry int NOT NULL,
	ID_ruchu int NOT NULL,
	ruch VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID_gry, ID_ruchu),
	FOREIGN KEY (ID_gry) REFERENCES Gry(ID_gry)
);	







