
--insert into pacijenti(id_korisnika, ime, prezime, email, lozinka, adresa, grad, drzava, broj_telefona, broj_osiguranika) values 
-- (1, 'Pera', 'Peric','pera@gmail.com','1234','Perina 10', 'Novi Sad', 'Srbija', '062555888', 123);
 
 
--insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id_korisnika) values 
-- (1, 1.05, 'A+', 55.5, 170.00,1);

INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_ADMINKC');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMINK');
INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_LEKAR');
INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_MS');
INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_PACIJENT');

INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (1, 'kor1@nesto.com', '$2a$10$muToaJQ.dwEA6N0m5pWK.Olvh5Hv3ISc7Ey09z7did.8MJJAT.lJW', 'Marko', 'Markovic', 'kor1@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (2, 'kor2@nesto.com', '$2a$04$V1cQC0KkbsXN9lMFZODesett3rfcV./sCmo3Tw67ovOq4mOyVGdUa', 'Marko', 'Markovic', 'kor2@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (3, 'kor3@nesto.com', '$2a$04$52k6A0/UyGxOynirBrA0Rujaf3P7.CjK6EvQYm4.IdOVHtxTegqRW', 'Marko', 'Markovic', 'kor3@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (4, 'kor4@nesto.com', '$2a$04$KqyVa3roUm.huHtwyuMqAeYoeKYSLG02chedIZwwRijp5UpWuNv4K', 'Marko', 'Markovic', 'kor4@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (5, 'kor5@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Marko', 'Markovic', 'kor5@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (6, 'kor6@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Jovana', 'Markovic', 'kor6@nesto.com', 'Perina 12', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');

insert into pacijenti(id,broj_osiguranika) values (5 ,123);
--insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id_korisnika) values (1, 1.05, 'A+', 55.5, 170.00, 5);

insert into klinicki_centar(id_klinickog_centra) values(1);
 
insert into sifrarnik_lekova(idsl,naziv_leka,sifra_leka,klinicki_centar)values(1,'Brufen', '1l',1);

insert into sifrarnik_dijagnoza(idsd,naziv_dijagnoze, sifra_dijagnoze, klinicki_centar)values(1, 'Grip', '1g',1);

insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_klinickog_centra)values(1,'Balzakova 1', 'Novi Sad', 'Klinika1', 'PET', 'odlicna', 1);
insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_klinickog_centra)values(2,'Balzakova 1', 'Beograd', 'Klinika1', 'JEDAN', 'odlicna', 1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 2500.00, 'DERMATOLOGIJA',1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 2500.00, 'KARDIOLOGIJA',2);

--insert into medicinska_sestra(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,id_klinike )values(2, 'Mikina 1','021555222', 'Srbija', 'mika@gmail.com', 'Beograd', 'Mika','mika123', 'Mikic',1 );
insert into lekar(id, ocena, id_klinike, tip_pregleda)values(3,'PET',1 ,'DERMATOLOGIJA');

--insert into lekar(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,ocena, id_klinike, tip_pregleda)values(3, 'Mikina 12','02188888', 'Srbija', 'jova@gmail.com', 'Beograd', 'Jova','jova123', 'Jovic','PET',1 ,'DERMATOLOGIJA');


--insert into radni_kalendarms(id_radnog_kalendara, id)values(1,4);

insert into radni_kalendarl(id_radnog_kalendara, id)values(1,3);


insert into administrator_klinickog_centra(id, id_klinickog_centra)values(1,1);

insert into administrator_klinike(id, id_klinike)values(2,1);

insert into medicinska_sestra(id, id_klinike )values(4,1 );
insert into medicinska_sestra(id, id_klinike )values(6,1 );

insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (1, 10, 'Sala10', 1);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (2, 11, 'Sala11', 1);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (3, 12, 'Sala12', 2);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (4, 13, 'Sala13', 2);

insert into pregled(id_pregleda, tip_pregleda, trajanje, vreme, id_klinike, id, id_sale)values(1, 'PEDIJATRIJA', 1, '2020-5-1 12:00:00', 1, 5, 1);
insert into operacija(id_operacije, dodatne_infoooperaciji, trajanje, vreme_operacije, id_klinike, id_sale, id)values(1, 'operacija tetive', 2, '2020-6-1 12:00:00', 2, 3, 5);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 4);














