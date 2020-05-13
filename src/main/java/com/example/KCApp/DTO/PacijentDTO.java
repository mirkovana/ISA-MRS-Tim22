package com.example.KCApp.DTO;

import com.example.KCApp.beans.Pacijent;

public class PacijentDTO {

	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private Integer idKorisnika;
	private int brojOsiguranika;
	//private ZdravstveniKartonDTO zdravstveniKarton;

	public PacijentDTO() {
	}

	public PacijentDTO(Pacijent pacijent) {
		ime = pacijent.getIme();
		prezime = pacijent.getPrezime();
		email = pacijent.getEmail();
		lozinka = pacijent.getLozinka();
		adresa = pacijent.getAdresa();
		grad = pacijent.getGrad();
		drzava = pacijent.getDrzava();
		brojTelefona = pacijent.getBrojTelefona();
		idKorisnika = pacijent.getIdKorisnika();
		brojOsiguranika = pacijent.getBrojOsiguranika();
		//zdravstveniKarton = new ZdravstveniKartonDTO(pacijent.getZdravstveniKarton());
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

	public Integer getIdKorisnika() {
		return idKorisnika;
	}

	public void setIdKorisnika(Integer idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	public int getBrojOsiguranika() {
		return brojOsiguranika;
	}

	public void setBrojOsiguranika(int brojOsiguranika) {
		this.brojOsiguranika = brojOsiguranika;
	}

	/*public ZdravstveniKartonDTO getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKartonDTO zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}*/
	
	
}
