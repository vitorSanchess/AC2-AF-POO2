INSERT INTO tb_user (name, email) VALUES ('Leda', 'ledaeda@gmail.com')
INSERT INTO tb_user (name, email) VALUES ('Plinio', 'plinio26@hotmail.com')
INSERT INTO tb_user (name, email) VALUES ('Jubileu', 'leujubileu@gmail.com')
INSERT INTO tb_user (name, email) VALUES ('Jorge', 'jorgedasilva@gmail.com')
INSERT INTO tb_user (name, email) VALUES ('Ana', 'claraana@hotmail.com')
INSERT INTO tb_user (name, email) VALUES ('Jandira', 'jandiracunha@gmail.com')

INSERT INTO tb_admin (user_id, phone_Number) VALUES ('1', '(11) 954867325')
INSERT INTO tb_admin (user_id, phone_Number) VALUES ('2', '(15) 689823187')
INSERT INTO tb_admin (user_id, phone_Number) VALUES ('3', '(17) 954846214')

INSERT INTO tb_attendee (user_id, balance) VALUES ('4', '120.0')
INSERT INTO tb_attendee (user_id, balance) VALUES ('5', '44.95')
INSERT INTO tb_attendee (user_id, balance) VALUES ('6', '0.00')

INSERT INTO tb_event (name, description, start_Date, end_Date, start_Time, end_Time, email_Contact) VALUES ('Bailão', 'Baile na comunidade', '2021-04-16', '2021-04-15', '18:00:00', '02:00:00', 'djazeitona@gmail.com')
INSERT INTO tb_event (name, description, start_Date, end_Date, start_Time, end_Time, email_Contact) VALUES ('Festa Junina', 'Quentão e pipoca', '2020-06-18', '2021-06-20', '16:30:00', '23:00:00', 'alegriaeventos@hotmail.com')
INSERT INTO tb_event (name, description, start_Date, end_Date, start_Time, end_Time, email_Contact) VALUES ('Oktoberfest', 'Cerveja e salsichão', '2021-09-18', '2021-10-03', '13:30:00', '22:00:00', 'facneseventos@gmail.com')

INSERT INTO tb_place (name, address) VALUES ('Predio x', 'rua x numero 123')
INSERT INTO tb_place (name, address) VALUES ('Predio y', 'rua y numero 456')
INSERT INTO tb_place (name, address) VALUES ('Predio z', 'rua z numero 789')

INSERT INTO tb_ticket (type, date, price) VALUES (0, CONVERT(CURRENT_TIMESTAMP,DATE), 0.0)
INSERT INTO tb_ticket (type, date, price) VALUES (1, CONVERT(CURRENT_TIMESTAMP,DATE), 150.0)
INSERT INTO tb_ticket (type, date, price) VALUES (1, CONVERT(CURRENT_TIMESTAMP,DATE), 49.90)