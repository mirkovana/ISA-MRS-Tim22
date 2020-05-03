package com.example.KCApp.DTO;

import com.example.KCApp.beans.AdministratorKlinike;

public class AdministratorKlinikeDTO {
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private Integer idKorisnika;
	private Integer klinika;



	public AdministratorKlinikeDTO(AdministratorKlinike admin) {
		ime = admin.getIme();
		prezime = admin.getPrezime();
		email = admin.getEmail();
		lozinka = admin.getLozinka();
		adresa = admin.getAdresa();
		grad = admin.getGrad();
		drzava = admin.getDrzava();
		brojTelefona = admin.getBrojTelefona();
		idKorisnika = admin.getIdKorisnika();
		klinika =admin.getKlinika().getIdKlinike();
	}

	public AdministratorKlinikeDTO() {

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

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
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

	public Integer getIdKorisnika() {
		return idKorisnika;
	}

	public void setIdKorisnika(Integer idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

}
