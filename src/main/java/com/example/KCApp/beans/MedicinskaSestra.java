package com.example.KCApp.beans;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "medicinskaSestra")
@SequenceGenerator(name = "medicinskaSestraIdSeq", sequenceName = "medicinskaSestraIdGen", initialValue = 1, allocationSize = 1)
public class MedicinskaSestra extends Korisnik {
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "idRadnogKalendara")	
	private RadniKalendar radniKalendar;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;

	public MedicinskaSestra(String ime, String prezime, String email, String lozinka, String adresa, String grad,
			String drzava, String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);

	}

	public MedicinskaSestra(String ime, String prezime, String email, String lozinka, String adresa, String grad,
			String drzava, String brojTelefona, RadniKalendar radniKalendar) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.radniKalendar = radniKalendar;
	}

	public RadniKalendar getRadniKalendar() {
		return radniKalendar;
	}

	public void setRadniKalendar(RadniKalendar radniKalendar) {
		this.radniKalendar = radniKalendar;
	}

}
