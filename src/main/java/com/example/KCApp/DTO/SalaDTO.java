package com.example.KCApp.DTO;

import com.example.KCApp.beans.Sala;


public class SalaDTO {
	
	private String nazivSale;
	private int brojSale;
	private Integer idSale;
	private Integer klinika;
	
	public SalaDTO() {
	}
	
	public SalaDTO(Sala sala) {
		nazivSale = sala.getNazivSale();
		brojSale = sala.getBrojSale();
		idSale = sala.getIdSale();
		klinika = sala.getKlinika().getIdKlinike();
	}

	public String getNazivSale() {
		return nazivSale;
	}

	public void setNazivSale(String nazivSale) {
		this.nazivSale = nazivSale;
	}

	public int getBrojSale() {
		return brojSale;
	}

	public void setBrojSale(int brojSale) {
		this.brojSale = brojSale;
	}

	public Integer getIdSale() {
		return idSale;
	}

	public void setIdSale(Integer idSale) {
		this.idSale = idSale;
	}

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}

	

}
