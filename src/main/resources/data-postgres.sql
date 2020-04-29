
insert into pacijenti(id_korisnika, ime, prezime, email, lozinka, adresa, grad, drzava, broj_telefona, broj_osiguranika) values 
 (1, 'Pera', 'Peric','pera@gmail.com','1234','Perina 10', 'Novi Sad', 'Srbija', '062555888', 1);
 
 
insert into zdravstveni_karton(id_zdravstvenog_kartona, dioptrija, krvna_grupa,tezina,visina, id_korisnika) values 
 (1, 1.05, 'A+', 55.5, 170.00,1);


 
insert into sifrarnik_lekova(idsl,naziv_leka,sifra_leka)values(1,'Brufen', '1l');

insert into sifrarnik_dijagnoza(idsd,naziv_dijagnoze, sifra_dijagnoze)values(1, 'Grip', '1g');


insert into klinicki_centar(id_klinickog_centra, idsd, idsl) values(1,1,1);

insert into cenovnik(id_cenovnika, cena, tip_pregleda)values(1, 2500.00, 'DERMATOLOGIJA');

insert into klinika(id_klinike, adresa_klinike, grad_klinike, naziv_klinike, ocena, opis_klinike, id_cenovnika, id_klinickog_centra)values(1,'Balzakova 1', 'Novi Sad', 'Klinika1', 'PET', 'odlicna', 1,1);

insert into medicinska_sestra(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,id_klinike )values(2, 'Mikina 1','021555222', 'Srbija', 'mika@gmail.com', 'Beograd', 'Mika','mika123', 'Mikic',1 );

insert into lekar(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka, prezime,ocena, id_klinike)values(3, 'Mikina 12','02188888', 'Srbija', 'jova@gmail.com', 'Beograd', 'Jova','jova123', 'Jovic','PET',1 );

insert into radni_kalendarms(id_radnog_kalendara, id_korisnikam)values(1,2);

insert into radni_kalendarl(id_radnog_kalendara, id_korisnika)values(1,3);


insert into administrator_klinickog_centra(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka,prezime,id_klinickog_centra)values(4,'Adminova 1', '021555999','Srbija','admin1@gmail.com', 'Zrenjanin','Marko','12345','Markovic',1);

insert into administrator_klinike(id_korisnika, adresa, broj_telefona, drzava, email, grad, ime, lozinka,prezime,id_klinike)values(4,'Adminova 5', '0611111111','Srbija','admin15@gmail.com', 'Subotica','Jovica','12345','Markovic',1);



















