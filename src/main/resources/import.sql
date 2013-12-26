-- Insert samples into the database(HSQL). 

insert into appuser (account, password, fullName, email) values ('admin', '1234', 'Admin', 'admin@your.com');
insert into appuser (account, password, fullName, email) values ('zkoss', '1234', 'ZKOSS', 'info@zkoss.org');


insert into apptodo (subject, priority, complete) values ('Buy some milk', 2, 0);
insert into apptodo (subject, priority, complete) values ('Gift for Dennis', 1, 0);
insert into apptodo (subject, priority, complete) values ('Pay credit-card bill', 0, 0);

insert into RACES values (1, 60, 50, 'Malabe', 6, curdate(), 'A-001', curtime(), 40, 30, 100);

insert into RACE_DETAILS values (null, 1, 'Good morning star', 'Jockey1', 2, 1/24, 'Trainer1', 1);
insert into RACE_DETAILS values (null, 2, 'Janakan', 'Jockey2', 1, 1/2, 'Trainer2', 1);
insert into RACE_DETAILS values (null, 3, 'Sunanda', 'Jockey3', 3, 1/6, 'Trainer3', 1);
insert into RACE_DETAILS values (null, 4, 'Kasun', 'Jockey4', 6, 1/7, 'Trainer4', 1);
insert into RACE_DETAILS values (null, 5, 'Julius', 'Jockey5', 4, 1/4, 'Trainer5', 1);
insert into RACE_DETAILS values (null, 6, 'Hemantha', 'Jockey6', 5, 1/10, 'Trainer6', 1);