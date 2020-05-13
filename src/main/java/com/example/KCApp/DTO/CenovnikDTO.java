package com.example.KCApp.DTO;

import com.example.KCApp.beans.Cenovnik;

import com.example.KCApp.beans.TipPregleda;

public class CenovnikDTO {
	private Integer idCenovnika;
	private TipPregleda tipPregledaCenovnik;
	private double cena;
	private KlinikaDTO klinika;
	
	public CenovnikDTO() {

	}
	public CenovnikDTO(Cenovnik cenovnik) {
		
		idCenovnika = cenovnik.getIdCenovnika();
		tipPregledaCenovnik = cenovnik.getTipPregledaCenovnik();
		cena = cenovnik.getCena();
		klinika= new KlinikaDTO(cenovnik.getKlinika());
		
	}
	public KlinikaDTO getKlinika() {
		return klinika;
	}
	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
	public Integer getIdCenovnika() {
		return idCenovnika;
	}
	public void setIdCenovnika(Integer idCenovnika) {
		this.idCenovnika = idCenovnika;
	}
	public TipPregleda getTipPregledaCenovnik() {
		return tipPregledaCenovnik;
	}
	public void setTipPregledaCenovnik(TipPregleda tipPregledaCenovnik) {
		this.tipPregledaCenovnik = tipPregledaCenovnik;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	
	
	
}
