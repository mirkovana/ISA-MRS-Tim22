package com.example.KCApp.DTO;

import com.example.KCApp.beans.ZdravstveniKarton;

public class ZdravstveniKartonDTO {

	private Integer idZdravstvenogKartona;
	private double tezina;
	private double visina;
	private double dioptrija;
	private String krvnaGrupa;
	
	public ZdravstveniKartonDTO() {}
	
	public ZdravstveniKartonDTO(ZdravstveniKarton zdravstveniKarton) {
		idZdravstvenogKartona = zdravstveniKarton.getIdZdravstvenogKartona();
		tezina = zdravstveniKarton.getTezina();
		visina = zdravstveniKarton.getVisina();
		dioptrija = zdravstveniKarton.getDioptrija();
		krvnaGrupa = zdravstveniKarton.getKrvnaGrupa();
	}

	public Integer getIdZdravstvenogKartona() {
		return idZdravstvenogKartona;
	}

	public void setIdZdravstvenogKartona(Integer idZdravstvenogKartona) {
		this.idZdravstvenogKartona = idZdravstvenogKartona;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(double dioptrija) {
		this.dioptrija = dioptrija;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}
	
	
	
}
