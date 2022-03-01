-- Suppression des tables existantes.
drop table if exists answers, results, users, authorities, user_authority, quizzes, questions, items, quiz_question;

-- Création des nouvelles tables.
create table if not exists authorities (
	id serial primary key,
	name varchar(255) not null
);

create table if not exists users (
	id serial primary key,
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	company varchar(255) not null,
	username varchar(255) not null unique,
    "password" char(60) not null,
    enabled boolean
);

create table if not exists user_authority (
	user_id int not null,
	authority_id int not null,
	foreign key (user_id) references users(id) on delete cascade on update cascade,
	foreign key (authority_id) references authorities(id) on delete cascade on update cascade
);

create table if not exists quizzes
(
	id serial primary key,
	title varchar(255) not null,
	description varchar(500) not null
);

create table if not exists questions
(
	id serial primary key,
	title varchar(255) not null
);

create table if not exists quiz_question 
(
	quiz_id int not null,
	question_id int not null,
	foreign key (quiz_id) references quizzes(id) on delete cascade on update cascade,
	foreign key (question_id) references questions(id) on delete cascade on update cascade
);

create table if not exists items 
(
	id serial primary key,
	question_id int not null,
	title varchar(255) not null,
	response boolean not null,
	foreign key (question_id) references questions(id) on delete cascade on update cascade
);

create table if not exists results (
	id serial primary key,
	user_id int not null,
	quiz_id int not null,
	date_debut timestamp not null,
	date_fin timestamp not null,
	foreign key (user_id) references users(id) on delete cascade on update cascade,
	foreign key (quiz_id) references quizzes(id) on delete cascade on update cascade
);

create table if not exists answers 
(
	id serial primary key,
	result_id int,
	item_id int,
	response boolean,
	foreign key (result_id) references results(id) on delete cascade on update cascade,
	foreign key (item_id) references items(id) on delete cascade on update cascade
);

-- Insertion des valeurs
insert into authorities(name) values
('ADMIN'),
('STAGIAIRE');
insert into users(firstname, lastname, company, username, "password", enabled) values 
('admin', 'admin', 'admin', 'admin', '$2a$10$ZCuOc.41Usq0YVgi.He4YOSWrtb1bFHag4ZU/bKc94lBAHoxwUhNa', true), 
('stagiaire', 'stagiaire', 'stagiaire', 'stagiaire', '$2a$10$UgSKVV8zdn6gFPCf3V3wA.u.OZDk8ylDVCQOwq8jsN8FggXh0NxKW', true),
('Joffrey', 'Benoist', 'Capgemini', 'jbenoist', '$2a$04$BM.6jb7Jld.Xu65lkdFheOXAmt2xVbkt9gI/fVfBc1cXM1d0ykgxe', true),
('Baptiste', 'Cros', 'Sogeti', 'bacros', '$2a$04$8B6o0z8cPVmEBCb0nWXI4u3N9Jte2se8AS30Z.Z1alzdrThimagGW', true),
('Loïc', 'Person', 'Capgemini', 'lperson', '$2a$04$XWMDjRrKRwxV/4/aFpZ7kOaUdIdvqACRNjcFg/RI3JSh0XbE2m5AO', true);
insert into user_authority(user_id, authority_id) values
(1, 1),
(2, 2),
(3, 1),
(4, 1),
(5, 2);
insert into quizzes(title, description) values 
('Programmation Java', 'Questionnaire sur les notions de base du langage Java'),
('Programmation Python', 'Questionnaire sur les notions de base du langage Python'),
('Programmation JavaScript', 'Questionnaire sur les notions de base du langage JavaScript');
insert into questions(title) values 
('Quelle est la syntaxe correcte pour afficher "Hello World!" dans la console ?'),
('Java est l''abréviation de JavaScript.'),
('En Java, les commentaires sont sous la forme :'),
('Quelle classe est utilisée pour les variables stockant du texte ?'),
('Quelle méthode est utilisée pour déterminer la longueur d''une chaîne de caractères ?'),
('Parmi les mots-clés suivant, lesquels sont issus du langage Java ?'),

('Python est un langage _____ ?'),
('En Python 3, que fait l''opérateur // ?'),
('Quelle fonction est utilisée pour ouvrir le fichier en lecture en Python ?'),
('En Python, quelle est la bonne méthode pour charger un module ?');
insert into quiz_question(quiz_id, question_id) values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10);
insert into items(question_id, title, response) values
(1, 'print("Hello World!")', false),
(1, 'System.out.print("Hello World!");', true),
(1, 'console.log("Hello World!");', false),
(1, 'echo HelloWorld!', false),
(2, 'Vrai', false),
(2, 'Faux', true),
(3, '// commentaire', true),
(3, '# commentaire', false),
(3, '/* commentaire */', true),
(3, '-- commentaire', false),
(4, 'string', false),
(4, 'Text', false),
(4, 'String', true),
(5, 'size()', false),
(5, 'length()', true),
(5, 'len()', false),
(6, 'class', true),
(6, 'def', false),
(6, 'function', false),
(6, 'static', true),
(6, 'const', true),
(7, 'compilé', false),
(7, 'interprété', true),
(7, 'machine', false),
(8, 'Reste de la division', false),
(8, 'Multiplication', false),
(8, 'Division entière', true),
(9, 'fopen(file_name, mode)', false),
(9, 'open(file_name, mode)', true),
(9, 'openfile(file_name, mode)', false),
(9, 'open_file(file_name, mode)', false),
(10, 'include math', false),
(10, 'using math', false),
(10, '#include math.h', false),
(10, 'import math', true);
insert into results(user_id, quiz_id, date_debut, date_fin) values
(2, 1, '2022-02-07 14:29:51', '2022-02-07 14:31:04');
insert into answers(result_id, item_id, response) values
(1, 1, false),
(1, 2, true),
(1, 3, false),
(1, 4, false),
(1, 5, false),
(1, 6, true),
(1, 7, true),
(1, 8, false),
(1, 9, false),
(1, 10, false),
(1, 11, false),
(1, 12, false),
(1, 13, true),
(1, 14, true),
(1, 15, false),
(1, 16, false),
(1, 17, true),
(1, 18, false),
(1, 19, false),
(1, 20, true),
(1, 21, true);