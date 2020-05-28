USE FilmRaiting;

DROP TABLE Reviews;
DROP TABLE Raitings;
DROP TABLE Films;
DROP TABLE Actors;
DROP TABLE Directors;
DROP TABLE Users;

CREATE TABLE Users
(
user_id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(50) NOT NULL,
age TINYINT NOT NULL,
login VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
PRIMARY KEY (user_id)
);

insert into Users
(name, age, login, password)
values
('Mark', '23', 'mark23', '23mark23'),
('Tom', '40', 'tom40', '40tom40'),
('Jhon', '33', 'jhon33', '33jhon33'),
('Bobby', '55', 'bobby55', '55bobby55');

select*from Users;

CREATE TABLE Actors
(
actor_id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(50) NOT NULL,
age TINYINT NOT NULL,
birthday DATE NOT NULL,
PRIMARY KEY (actor_id)
);

INSERT INTO Actors
(name, age, birthday)
VALUES
('Arnold Alois Schwarzenegger', 72, '1947-07-30'),
('Sam Worthington', 43, '1976-08-02');

select*from Actors;

CREATE TABLE Directors
(
director_id INT AUTO_INCREMENT NOT NULL,
name VARCHAR (50) NOT NULL,
age TINYINT NOT NULL,
birthday DATE NOT NULL,
PRIMARY KEY (director_id)
);

INSERT INTO Directors
(name, age, birthday)
VALUES
('James Cameron', 65, '1954-08-16'),
('Joseph McGinty Nichol', 51, '1968-08-09');

select*from Directors;

CREATE TABLE Films
(
film_id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(50) NOT NULL,
genre VARCHAR(20) NOT NULL,
releaseDate DATE NOT NULL,
actor_id INT NOT NULL,
director_id INT NOT NULL,
FOREIGN KEY (actor_id) REFERENCES actors (actor_id),
FOREIGN KEY (director_id) REFERENCES Directors (director_id),
primary key(film_id)
);

INSERT INTO Films
(name, genre, releaseDate, actor_id, director_id)
values
('Terminator', 'Action', '1984-10-26', 1, 1),
('Terminator 2', 'Action', '1991-06-03', 1, 1),
('Terminator 3', 'Action', '2003-07-02', 1, 1),
('Terminator 4', 'Action', '2009-05-21', 2, 2);

select*from films;

CREATE TABLE Reviews
(
review_id INT AUTO_INCREMENT NOT NULL,
user_id INT NULL,
film_id INT NOT NULL,
review VARCHAR(300) NULL,
date DATETIME NULL,
FOREIGN KEY (film_id) REFERENCES Films (film_id),
PRIMARY KEY (review_id)
);

ALTER TABLE Reviews ADD CONSTRAINT
	FK_Reviews_user_id FOREIGN KEY(user_id) 
	REFERENCES Users (user_id) 
		ON UPDATE  CASCADE 
		ON DELETE  SET NULL;

INSERT INTO Reviews
(user_id, film_id, review, date)
VALUES
(1, 1, 'As always, the first film is the birst from sequels', NOW()),
(1, 3, 'Not bad. I am satisfied=)', NOW()),
(3, 2, 'I am happy. Arnold is thr best actor I have ever seen', NOW());

select*from Reviews;

CREATE TABLE Raitings
(
raiting_id INT AUTO_INCREMENT NOT NULL,
film_id INT NOT NULL,
user_id INT NULL,
raiting INT NOT NULL,
PRIMARY KEY (raiting_id),
FOREIGN KEY (film_id) REFERENCES Films (film_id)
);

ALTER TABLE Raitings ADD CONSTRAINT
FK_Raitings_user_id FOREIGN KEY (user_id) 
REFERENCES Users (user_id)
ON UPDATE CASCADE
ON DELETE SET NULL;

INSERT INTO Raitings 
(film_id, user_id, raiting)
VALUES
(1, 1, 9),
(1, 3, 8),
(3, 2, 10);

select*from Raitings;

SELECT Users.user_id, name , age, review, date
   	  FROM Users
INNER JOIN Reviews
      ON Users.user_id = Reviews.user_id
	  GROUP BY Users.user_id, Reviews.review;
      
     SELECT Users.user_id, Users.name , age, Films.name, review, date 
     FROM Users
     JOIN Reviews
      ON Users.user_id = Reviews.user_id
      JOIN Films
      ON Reviews.film_id = Films.film_id
      GROUP BY Films.film_id, Users.user_id, Reviews.review;
      
      SELECT Users.user_id, Users.name, Films.name, Raitings.raiting, review, date, raiting 
      From Users
      JOIN Reviews
      ON Users.user_id = Reviews.review_id
      JOIN Films
      ON Reviews.film_id = Films.film_id
      JOIN Raitings
      ON Films.film_id = Raitings.film_id
      GROUP BY Raitings.raiting, Users.user_id, Reviews.review;
      
      

