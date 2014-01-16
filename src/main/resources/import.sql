-- Insert samples into the database(HSQL). 

insert into appuser (account, password, fullName, email) values ('admin', '1234', 'Admin', 'admin@your.com');
insert into appuser (account, password, fullName, email) values ('zkoss', '1234', 'ZKOSS', 'info@zkoss.org');
insert into appuser (account, password, fullName, email) values ('suhan', '1234', 'Suhan', 'suhan@zkoss.org');
insert into appuser (account, password, fullName, email) values ('sashika', '1234', 'Sashika', 'sashika@zkoss.org');


insert into apptodo (subject, priority, complete) values ('Buy some milk', 2, 0);
insert into apptodo (subject, priority, complete) values ('Gift for Dennis', 1, 0);
insert into apptodo (subject, priority, complete) values ('Pay credit-card bill', 0, 0);

-- RACE_SERIAL_NO, FIRST_PLACE_AMOUNT, FOURTH_PLACE_AMOUNT,
-- MEETING_PLACE, NUMBER_OF_HORSES, RACE_DATE, RACE_ID, RACE_TIME,
-- SECOND_PLACE_AMOUNT, THIRD_PLACE_AMOUNT, WINNERS_WIN_AMOUNT
insert into RACES values (1, 60, 50, 'Race_Course_Colombo', 5, curdate(), 'A-001', curtime(), 40, 30, 100);
insert into RACES values (2, 60, 50, 'Race_Course_Kandy', 3, curdate(), 'A-002', curtime(), 40, 30, 100);
insert into RACES values (3, 60, 50, 'Race_Course_Galle', 2, curdate(), 'A-003', curtime(), 40, 30, 100);
insert into RACES values (4, 60, 50, 'Race_Course_Matara', 3, curdate(), 'A-004', curtime(), 40, 30, 100);
insert into RACES values (5, 60, 50, 'Race_Course_Jaffna', 4, curdate(), 'A-005', curtime(), 40, 30, 100);

insert into RACE_DETAILS values (null, 1, 'Nine Realms', 'W J Haggas', 2, 1/24, 'J Doyle', 1);
insert into RACE_DETAILS values (null, 2, 'Famous Poet (IRE)', 'Saeed bin Suroor', 1, 1/2, 'Joao Moreira', 1);
insert into RACE_DETAILS values (null, 3, 'Romeo Bravo', 'Stan Elley', 3, 1/6, 'J Smitsdorff', 1);
insert into RACE_DETAILS values (null, 4, 'Good Morning Star', 'Tom Elliot', 5, 1/7, 'Santoz Sarvez', 1);
insert into RACE_DETAILS values (null, 5, 'Kiri Sudaa', 'Saiman Nawagaththegama', 4, 1/7, 'Thilanga Sumathipala', 1);

insert into RACE_DETAILS values (null, 1, 'Sunny Boy (US)', 'W K Moreira', 2, 1/8, 'La Bamba', 2);
insert into RACE_DETAILS values (null, 2, 'Loala (UK)', 'H K Hills', 1, 1/3, 'Jeremy Lamberta', 2);
insert into RACE_DETAILS values (null, 3, 'Game of Thrones', 'S K Peins', 3, 1/15, 'Jimmy Anderson', 2);

insert into RACE_DETAILS values (null, 1, 'John Diere', 'K K Wells', 2, 1/8, 'Kills Kein', 3);
insert into RACE_DETAILS values (null, 2, 'Mahindra (IN)', 'Nadia Ali', 1, 1/3, 'Ranjith Singh', 3);

insert into RACE_DETAILS values (null, 1, 'Handaya', 'A C Bandara', 2, 1/8, 'Lambert', 4);
insert into RACE_DETAILS values (null, 2, 'Kiriya', 'Siripala', 1, 1/3, 'Jemis Banda', 4);
insert into RACE_DETAILS values (null, 3, 'Sandapa', 'Akila Mendis', 3, 1/15, 'Martin Wickramasinghe', 4);

insert into RACE_DETAILS values (null, 1, 'Carl Reggazoni', 'Ferrari', 2, 1/24, 'J Doyle', 5);
insert into RACE_DETAILS values (null, 2, 'Nikki Lauda (AUS)', 'Ferrari', 1, 1/2, 'Joao Moreira', 5);
insert into RACE_DETAILS values (null, 3, 'James Hunt (UK)', 'Maclaren', 3, 1/6, 'J Smitsdorff', 5);
insert into RACE_DETAILS values (null, 4, 'Ayrton Senna', 'Williams', 5, 1/7, 'Santoz Sarvez', 5);