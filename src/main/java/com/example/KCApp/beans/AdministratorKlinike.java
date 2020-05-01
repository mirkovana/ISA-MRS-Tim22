package com.example.KCApp.beans;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "administratorKlinike")
public class AdministratorKlinike extends Korisnik{

	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	public AdministratorKlinike(String ime, String prezime, String email, String lozinka, String adresa, String grad,
			String drzava, String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
	}
	
	
	public AdministratorKlinike() {
		super();
	}


	public AdministratorKlinike(String ime, String prezime, String email, String lozinka, String adresa, String grad,
			String drzava, String brojTelefona, Klinika klinika) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.klinika = klinika;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
}
