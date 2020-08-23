# ISA-MRS-Tim22
Repozitorijum za potrebe projekta iz predmeta ISA/MRS, SIIT 2019/2020
Marina Guša SW49/2017
Marija Kastratović SW43/2017
Ana Mirkov SW72/2017

Informacioni sistem klinickog centra. Osnovna namena aplikacije je vodjenje evidencije
o zaposlenima, registrovanim klinikama, salama za preglede i operacije,
pacijentima i njihovim zdravstvenim kartonima, kao i zakazivanje pregleda.


Serverska platforma: Java(1.8) + Spring Boot

Klijentska platforma: Klasicna web aplikacija

Za pokretanje projekta neophodni su Maven, Hibernate i PostgreSQL baza podataka.
Skripta za popunu baze data-postgres.sql nalazi se u okviru projekta, u njoj se nalaze podaci potrebni za testiranje aplikacije.

Pokrenutoj aplikaciji se pristupa na  http://localhost:8080/pocetnaStranica.html

Podaci o korisnicima neophodni za prijavu na sistem:


1. Administrator klinickog centra - email: kor1@nesto.com, lozinka: 123

2. Administrator klinike - email: kor2@nesto.com, lozinka: 1

3. Lekar - email: kor3@nesto.com, lozinka: 2

4. Medicinska sestra - email: kor4@nesto.com, lozinka: 3

5. Pacijent - email: kor5@nesto.com, lozinka: 4

Napomena: Izmene na profilima za svaki tip korisnika su vidljive tek nakon sto se korisnik odjavi i ponovo prijavi. 
Prilikom kreiranja zahteva za odmor/odsustvo datum treba da bude u formatu "yyyy-MM-dd".
Prilikom kreiranja unapred definisanih pregleda datum treba da bude u formatu "yyyy-MM-dd'T'HH:mm:ss.SSSZ" ili  "yyyy-MM-dd".
