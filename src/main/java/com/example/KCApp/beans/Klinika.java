package com.example.KCApp.beans;

import java.util.List;

public class Klinika {

	private int idKlinike;
	private String naziv;
	private String adresa;
	private String grad;
	private String opis;
	private List<Lekar> lekari; 
	private List<Sala> sale;
	private Cenovnik cenovnik;
	private Ocena ocena;
	
	public Klinika() {}
	
	public Klinika(String naziv, String adresa, String grad, String opis, List<Lekar> lekari, List<Sala> sale,
			Ocena ocena) {
		this.naziv = naziv;
		this.adresa = adresa;
		this.grad = grad;
		this.opis = opis;
		this.lekari = lekari;
		this.sale = sale;
		this.ocena = ocena;
	}

	public int getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(int idKlinike) {
		this.idKlinike = idKlinike;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(List<Lekar> lekari) {
		this.lekari = lekari;
	}

	public List<Sala> getSale() {
		return sale;
	}

	public void setSale(List<Sala> sale) {
		this.sale = sale;
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}
}
