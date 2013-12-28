-- Insert samples into the database(HSQL). 

insert into appuser (account, password, fullName, email) values ('admin', '1234', 'Admin', 'admin@your.com');
insert into appuser (account, password, fullName, email) values ('zkoss', '1234', 'ZKOSS', 'info@zkoss.org');


insert into apptodo (subject, priority, complete) values ('Buy some milk', 2, 0);
insert into apptodo (subject, priority, complete) values ('Gift for Dennis', 1, 0);
insert into apptodo (subject, priority, complete) values ('Pay credit-card bill', 0, 0);

insert into RACES values (1, 60, 50, 'Race_Course_Colombo', 5, curdate(), 'A-001', curtime(), 40, 30, 100);

insert into RACE_DETAILS values (null, 1, 'Nine Realms', 'W J Haggas', 2, 1/24, 'J Doyle', 1);
insert into RACE_DETAILS values (null, 2, 'Famous Poet (IRE)', 'Saeed bin Suroor', 1, 1/2, 'Joao Moreira', 1);
insert into RACE_DETAILS values (null, 3, 'Romeo Bravo', 'Stan Elley', 3, 1/6, 'J Smitsdorff', 1);
insert into RACE_DETAILS values (null, 4, 'Good Morning Star', 'Tom Elliot', 5, 1/7, 'Santoz Sarvez', 1);
insert into RACE_DETAILS values (null, 5, 'Kiri Sudaa', 'Saiman Nawagaththegama', 4, 1/7, 'Thilanga Sumathipala', 1);
