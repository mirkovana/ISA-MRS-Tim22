package com.example.KCApp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Korisnik {
	
	@Column(name="imeKorisnika", unique=false, nullable=false)
	private String ime;
	@Column(name="prezimeKorisnika", unique=false, nullable=false)
	private String prezime;
	@Column(name="emailKorisnika", unique=true, nullable=false)
	private String email;
	@Column(name="lozinkaKorisnika", unique=false, nullable=false)
	private String lozinka;
	@Column(name="adresaKorisnika", unique=true, nullable=false)
	private String adresa;
	@Column(name="gradKorisnika", unique=false, nullable=false)
	private String grad;
	@Column(name="drzavaKorisnika", unique=false, nullable=false)
	private String drzava;
	@Column(name="brojTelefonaKorisnika", unique=true, nullable=false)
	private String brojTelefona;
	@Id
	@GeneratedValue
	@Column(name="idKorisnika", unique=true, nullable=false)
	private Integer idKorisnika;
	
	public Korisnik() {}
	
	public Korisnik(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
}
