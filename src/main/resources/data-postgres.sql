
insert into pacijenti(id_korisnika, ime, prezime, email, lozinka, adresa, grad, drzava, broj_telefona, broj_osiguranika) values 
 (1, 'Pera', 'Peric','pera@gmail.com','1234','Perina 10', 'Novi Sad', 'Srbija', '062555888', 123);
 
 
insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id_korisnika) values 
 (1, 1.05, 'A+', 55.5, 170.00,1);




insert into klinicki_centar(id_klinickog_centra) values(1);

 
insert into sifrarnik_lekova(idsl,naziv_leka,sifra_leka,klinicki_centar)values(1,'Brufen', '1l',1);

insert into sifrarnik_dijagnoza(idsd,naziv_dijagnoze, sifra_dijagnoze, klinicki_centar)values(1, 'Grip', '1g',1);



insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_klinickog_centra)values(1,'Balzakova 1', 'Novi Sad', 'Klinika1', 'PET', 'odlicna', 1);
insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_klinickog_centra)values(2,'Balzakova 1', 'Beograd', 'Klinika1', 'JEDAN', 'odlicna', 1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 2500.00, 'DERMATOLOGIJA',1);
insert into cenovnik(id_cenovnika, cena, tip_pregleda, id_klinike)values(nextval('cenovnik_id_seq'), 2500.00, 'KARDIOLOGIJA',2);

insert into medicinska_sestra(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,id_klinike )values(2, 'Mikina 1','021555222', 'Srbija', 'mika@gmail.com', 'Beograd', 'Mika','mika123', 'Mikic',1 );

insert into lekar(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,ocena, id_klinike, tip_pregleda)values(3, 'Mikina 12','02188888', 'Srbija', 'jova@gmail.com', 'Beograd', 'Jova','jova123', 'Jovic','PET',1 ,'DERMATOLOGIJA');

insert into radni_kalendarms(id_radnog_kalendara, id_korisnikam)values(1,2);

insert into radni_kalendarl(id_radnog_kalendara, id_korisnika)values(1,3);


insert into administrator_klinickog_centra(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka,prezime,id_klinickog_centra)values(4,'Adminova 1', '021555999','Srbija','admin1@gmail.com', 'Zrenjanin','Marko','12345','Markovic',1);

insert into administrator_klinike(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka,prezime,id_klinike)values(4,'Adminova 5', '0611111111','Srbija','admin15@gmail.com', 'Subotica','Jovica','12345','Markovic',1);

insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (1, 10, 'Sala10', 1);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (2, 11, 'Sala11', 1);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (3, 12, 'Sala12', 2);
insert into sala(id_sale, broj_sale, naziv_sale, id_klinike) values (4, 13, 'Sala13', 2);

insert into pregled(id_pregleda, tip_pregleda, trajanje, vreme, id_klinike, id_korisnika, id_sale)values(1, 'PEDIJATRIJA', 1, '2020-5-1 12:00:00', 1, 1, 1);
insert into operacija(id_operacije, dodatne_infoooperaciji, trajanje, vreme_operacije, id_klinike, id_sale, id_korisnika)values(1, 'operacija tetive', 2, '2020-6-1 12:00:00', 2, 3, 1);

















