package com.example.KCApp.DTO;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Ocena;

public class KlinikaDTO {

	private Integer idKlinike;
	private String naziv;
	private String adresa;
	private String grad;
	private String opis;
	private Ocena ocena;
	//private CenovnikDTO cenovnik;
	
	public KlinikaDTO() {
		
	}

	public KlinikaDTO(Klinika klinika) {
		
		idKlinike = klinika.getIdKlinike();
		naziv = klinika.getNaziv();
		adresa = klinika.getAdresa();
		grad = klinika.getGrad();
		opis = klinika.getOpis();
		ocena = klinika.getOcena();
		//cenovnik = new CenovnikDTO(klinika.getCenovnik());
	}

	public Integer getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(Integer idKlinike) {
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

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}

	@Override
	public String toString() {
		return "KlinikaDTO [idKlinike=" + idKlinike + ", naziv=" + naziv + ", adresa=" + adresa + ", grad=" + grad
				+ ", opis=" + opis + ", ocena=" + ocena + "]";
	}
	
}
