package com.example.KCApp.beans;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "lekar")
@SequenceGenerator(name = "lekarIdSeq", sequenceName = "lekarIdGen", initialValue = 1, allocationSize = 1)
public class Lekar extends Korisnik {

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "idRadnogKalendara")	
	private RadniKalendar radniKalendar;
	
	@Column(name="Ocena") 
	@Enumerated(EnumType.STRING)
	private Ocena ocena;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	//klasa ZahtevZaOdmor
		
	public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
	}

	public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona, RadniKalendar radniKalendar, Ocena ocena) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.radniKalendar = radniKalendar;
		this.ocena = ocena;
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}

	public RadniKalendar getRadniKalendar() {
		return radniKalendar;
	}

	public void setRadniKalendar(RadniKalendar radniKalendar) {
		this.radniKalendar = radniKalendar;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	
	
}
