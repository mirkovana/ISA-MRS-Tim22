package com.example.KCApp.DTO;

import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Ocena;
import com.example.KCApp.beans.TipPregleda;

public class LekarDTO {
	
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private Integer idKorisnika;
	private Ocena ocena;
	private TipPregleda tipPregleda;
	private RadniKalendarLDTO radniKalendarL;
	private Integer klinika;
	
	public LekarDTO() {
	}

	public LekarDTO(Lekar lekar) {
		ime = lekar.getIme();
		prezime = lekar.getPrezime();
		email = lekar.getEmail();
		lozinka = lekar.getLozinka();
		adresa = lekar.getAdresa();
		grad = lekar.getGrad();
		drzava = lekar.getDrzava();
		brojTelefona = lekar.getBrojTelefona();
		idKorisnika = lekar.getIdKorisnika();
		ocena = lekar.getOcena();
		tipPregleda = lekar.getTipPregleda();
		radniKalendarL = new RadniKalendarLDTO(lekar.getRadniKalendar());
		klinika = lekar.getKlinika().getIdKlinike();
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

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public RadniKalendarLDTO getRadniKalendarL() {
		return radniKalendarL;
	}

	public void setRadniKalendarL(RadniKalendarLDTO radniKalendarL) {
		this.radniKalendarL = radniKalendarL;
	}
	
	

}
