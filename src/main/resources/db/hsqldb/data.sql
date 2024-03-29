INSERT INTO matches (id,name,round,turn,votes_in_favor,votes_against,plays,finished, winner)VALUES (1, 'partida1', 3, 0, 0, 0, 0, true, 0);
INSERT INTO matches (id,name,round,turn,votes_in_favor,votes_against,plays,finished)VALUES (2, 'partida2', 0, 0, 0, 0, 0, false);

INSERT INTO users(email,username,password,enabled) VALUES ('admin@gmail.com','admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (1, 'player1', 0, null, null, 0, 'admin1',1,false);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (7, 'player1', null, null, null, 0, 'admin1',2,false);

INSERT INTO users(email,username,password,enabled) VALUES ('ppp@gmail.com','ppppp','ppppp',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'ppppp','admin');

INSERT INTO users(email,username,password,enabled) VALUES ('friend1@gmail.com','friend1','friend1',TRUE);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (2, 'player2', 0, null, null, 1, 'friend1',1,false);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (8, 'player2', null, null, null, 1, 'friend1',2,false);
INSERT INTO authorities(id,username,authority) VALUES (3,'friend1','user');

INSERT INTO users(email,username,password,enabled) VALUES ('friend2@gmail.com','friend2','friend2',TRUE);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (3, 'player3', 1, null, null, 2,'friend2',1,false);
INSERT INTO authorities(id,username,authority) VALUES (4,'friend2','user');

INSERT INTO users(email,username,password,enabled) VALUES ('friend3@gmail.com','friend3','friend3',TRUE);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (4, 'player4', 1, null, null, 2,'friend3',1,false);
INSERT INTO authorities(id,username,authority) VALUES (5,'friend3','user');

INSERT INTO users(email,username,password,enabled) VALUES ('friend4@gmail.com','friend4','friend4',TRUE);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (5, 'player5', 1, null, null, 3, 'friend4',1,false);
INSERT INTO authorities(id,username,authority) VALUES (6,'friend4','user');

INSERT INTO users(email,username,password,enabled) VALUES ('friend5@gmail.com','friend5','friend5',TRUE);
INSERT INTO players(id,name,card1,card2,vote,role,username,match_id,asigned) VALUES (6, 'player6', 1, null, null, 3, 'friend5',1,false);
INSERT INTO authorities(id,username,authority) VALUES (7,'friend5','user');

INSERT INTO invitations(id,match_id,username) VALUES (1,1,'ppppp');

INSERT INTO achievement_type(id, name) VALUES (1,'jugadas');
INSERT INTO achievement_type(id, name) VALUES (2,'ganadas');


INSERT INTO achievements(id,name,description, valor, achievement_type) VALUES (1,'Primera Partida', 'Juega tu primera partida de Idus Martii', 1, 1);
INSERT INTO achievements(id,name,description, valor, achievement_type) VALUES (2,'5 Partidas', 'Juega 5 partidas de Idus Martii', 5, 1);
INSERT INTO achievements(id,name,description, valor, achievement_type) VALUES (3,'Primera Victoria', 'Gana tu partidas de Idus Martii', 1, 2);

INSERT INTO achievement_user(user_username, achievements_id) VALUES ('admin1',1);

INSERT INTO friends(user_username,friends_username) VALUES ('admin1','friend1');
INSERT INTO friends(user_username,friends_username) VALUES ('friend1','admin1');
INSERT INTO friends(user_username,friends_username) VALUES ('admin1','friend2');
INSERT INTO friends(user_username,friends_username) VALUES ('friend2','admin1');
INSERT INTO friends(user_username,friends_username) VALUES ('admin1', 'friend3');
INSERT INTO friends(user_username,friends_username) VALUES ('friend3', 'admin1');


INSERT INTO friends_invitations(id,username,username2) VALUES (1,'friend1','ppppp');

INSERT INTO chats(id,text,date,username, match_id) VALUES (1,'hola1','2022-01-19','admin1',1);





