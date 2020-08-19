
--insert into pacijenti(id_korisnika, ime, prezime, email, lozinka, adresa, grad, drzava, broj_telefona, broj_osiguranika) values 
-- (1, 'Pera', 'Peric','pera@gmail.com','1234','Perina 10', 'Novi Sad', 'Srbija', '062555888', 123);

INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_ADMINKC');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMINK');
INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_LEKAR');
INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_MS');
INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_PACIJENT');

INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (1, 'kor1@nesto.com', '$2a$10$muToaJQ.dwEA6N0m5pWK.Olvh5Hv3ISc7Ey09z7did.8MJJAT.lJW', 'Jovan', 'Markovic', 'kor1@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (2, 'kor2@nesto.com', '$2a$04$V1cQC0KkbsXN9lMFZODesett3rfcV./sCmo3Tw67ovOq4mOyVGdUa', 'Marina', 'Jankovic', 'kor2@nesto.com', 'Perina 10', 'Beograd', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (3, 'kor3@nesto.com', '$2a$04$52k6A0/UyGxOynirBrA0Rujaf3P7.CjK6EvQYm4.IdOVHtxTegqRW', 'Dragan', 'Peric', 'kor3@nesto.com', 'Perina 10', 'Subotica', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (4, 'kor4@nesto.com', '$2a$04$KqyVa3roUm.huHtwyuMqAeYoeKYSLG02chedIZwwRijp5UpWuNv4K', 'Jelena', 'Milosevic', 'kor4@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (5, 'kor5@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Stefan', 'Petrovic', 'kor5@nesto.com', 'Perina 10', 'Zrenjanin', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (6, 'kor6@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Anabel', 'Nikolic', 'kor6@nesto.com', 'Perina 12', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (7, 'kor7@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Marija', 'Jovanovic', 'kor7@nesto.com', 'Perina 10', 'Beograd', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (8, 'kor8@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Darko', 'Djokovic', 'kor8@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (9, 'kor9@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Milos', 'Tipsarevic', 'kor9@nesto.com', 'Perina 10', 'Nis', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');
INSERT INTO USERS (id, username, password, ime, prezime, email, adresa, grad, drzava, broj_telefona, last_password_reset_date) VALUES (10,'kor10@nesto.com', '$2a$04$fuRxrbBYQ8vvxfufN3NRTuBEkZGiNMZZBBsLUV6rUDFFXvVT3naRK', 'Zarko', 'Raznatovic', 'kor10@nesto.com', 'Perina 10', 'Novi Sad', 'Srbija', '062555888', '2019-10-01 21:58:58.508-07');


insert into pacijenti(id, enabled, broj_osiguranika) values (5,true ,123);
insert into pacijenti(id, enabled ,broj_osiguranika) values (10 ,true, 111);
--insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id_korisnika) values (1, 1.05, 'A+', 55.5, 170.00, 5);

insert into klinicki_centar(id_klinickog_centra) values(1);
 
insert into sifrarnik_lekova(idsl,naziv_leka,sifra_leka,klinicki_centar)values(1,'Brufen', '1l',1);

insert into sifrarnik_dijagnoza(idsd,naziv_dijagnoze, sifra_dijagnoze, klinicki_centar)values(1, 'Grip', '1g',1);

insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_klinickog_centra)values(1,'Balzakova 1', 'Novi Sad', 'Klinika1', 5, 'odlicna', 1);
insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_klinickog_centra)values(2,'Balzakova 1', 'Beograd', 'AKlinika1', 1, 'odlicna', 1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 2500.00, 'DERMATOLOGIJA',1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 2500.00, 'KARDIOLOGIJA',2);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 3500.00, 'GINEKOLOGIJA',1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 1500.00, 'KARDIOLOGIJA',1);

--insert into medicinska_sestra(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,id_klinike )values(2, 'Mikina 1','021555222', 'Srbija', 'mika@gmail.com', 'Beograd', 'Mika','mika123', 'Mikic',1 );
insert into lekar(id, ocena, id_klinike, tip_pregleda)values(3,3.7,1 ,'DERMATOLOGIJA');
insert into lekar(id, ocena, id_klinike, tip_pregleda)values(7,3.5,1 ,'KARDIOLOGIJA');
insert into lekar(id, ocena, id_klinike, tip_pregleda)values(8,1,2,'GINEKOLOGIJA');
insert into lekar(id, ocena, id_klinike, tip_pregleda)values(9,3.9,1 ,'GINEKOLOGIJA');

--insert into lekar(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,ocena, id_klinike, tip_pregleda)values(3, 'Mikina 12','02188888', 'Srbija', 'jova@gmail.com', 'Beograd', 'Jova','jova123', 'Jovic','PET',1 ,'DERMATOLOGIJA');


--insert into radni_kalendarms(id_radnog_kalendara, id)values(1,4);

insert into radni_kalendarl(id_radnog_kalendara, id, datum_od, datum_do)values(1,3,'2020.08.22', '2020.08.29');
insert into radni_kalendarl(id_radnog_kalendara, id, datum_od, datum_do)values(2,7,'2020.08.22', '2020.08.29');
insert into radni_kalendarl(id_radnog_kalendara, id, datum_od, datum_do)values(3,8,'2020.08.22', '2020.08.29');
insert into radni_kalendarl(id_radnog_kalendara, id, datum_od, datum_do)values(4,9,'2020.08.22', '2020.08.29');


insert into administrator_klinickog_centra(id, id_klinickog_centra)values(1,1);

insert into administrator_klinike(id, id_klinike)values(2,1);

insert into medicinska_sestra(id, id_klinike )values(4,1 );
insert into medicinska_sestra(id, id_klinike )values(6,1 );

insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (1, 10, 'Sala10', 1);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (2, 11, 'Sala11', 1);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (3, 12, 'Sala12', 2);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (4, 13, 'Sala13', 2);

insert into pregled(id_pregleda, cena, tip_pregleda, trajanje, vreme, id_klinike, id, id_sale, id_lekara)values(1, 1000, 'KARDIOLOGIJA', 1, '2020-5-1 12:00:00', 1, 5, 1, 3);
insert into pregled(id_pregleda, cena, tip_pregleda, trajanje, vreme, id_klinike, id, id_sale, id_lekara)values(3, 1000, 'KARDIOLOGIJA', 1, '2020-5-1 16:00:00', 1, 10, 1, 3);
insert into pregled(id_pregleda, cena, tip_pregleda, trajanje, vreme, id_klinike, id, id_sale, id_lekara)values(2, 1000, 'DERMATOLOGIJA', 1, '2020-5-1 15:00:00', 1, null, 1, 3);
insert into operacija(id_operacije, dodatne_infoooperaciji, trajanje, vreme_operacije, id_klinike, id_sale, id, id_lekara)values(1, 'operacija tetive', 2, '2020-6-1 12:00:00', 2, 3, 5, 3);
insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id) values (1, 1.05, 'A+', 55.5, 170.00,5);
insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id) values (2, 1.05, 'B+', 55.5, 170.00,10);
insert into recept(id_recepta, overen,id,id_pregleda, id_sifrarnika)values(1,false,null,1,1);
insert into recept(id_recepta, overen,id,id_pregleda, id_sifrarnika)values(2,true,4,2,1);

--insert into recept(id_recepta, overen,id,id_pregleda,id_sifrarnika)values(2,false,null,4,2,1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (10, 5);













