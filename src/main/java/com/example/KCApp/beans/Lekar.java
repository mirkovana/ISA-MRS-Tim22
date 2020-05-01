package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "lekar")
@SequenceGenerator(name = "lekarIdSeq", sequenceName = "lekarIdGen", initialValue = 1, allocationSize = 1)
public class Lekar extends Korisnik {

	@OneToOne(fetch = LAZY, mappedBy="lekar")
	@JoinColumn(name = "idRadnogKalendara")	
	private RadniKalendarL radniKalendarL;
	
	@Column(name="Ocena") 
	@Enumerated(EnumType.STRING)
	private Ocena ocena;
	
	@ManyToOne(cascade = {ALL}, fetch = LAZY)
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	//klasa ZahtevZaOdmor
		
	public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
	}

	public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona, RadniKalendarL radniKalendarL, Ocena ocena) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.radniKalendarL = radniKalendarL;
		this.ocena = ocena;
	}

	public Lekar() {
		super();
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}

	public RadniKalendarL getRadniKalendar() {
		return radniKalendarL;
	}

	public void setRadniKalendar(RadniKalendarL radniKalendarL) {
		this.radniKalendarL = radniKalendarL;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	
	
}
