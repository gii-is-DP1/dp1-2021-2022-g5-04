INSERT INTO users(username,password,email,enabled) VALUES ('admin1','4dm1n','admin1@gmail.com',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users VALUES ('antsermen','antsermen@gmail.com',TRUE, 'antsermen1');
INSERT INTO player VALUES (1, 'player1', '1', '2', '1', '1', 'antsermen');
