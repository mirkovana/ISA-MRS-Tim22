package com.example.KCApp.DTO;

import com.example.KCApp.beans.SifrarnikLekova;

public class SifrarnikLekovaDTO {
	private String nazivLeka;
	private String sifraLeka;
	private Integer idSL;
	
	public SifrarnikLekovaDTO(SifrarnikLekova sifrarnik) {
		nazivLeka = sifrarnik.getNazivLeka();
		sifraLeka=sifrarnik.getSifraLeka();
		idSL= sifrarnik.getIdSL();
	}
	
	
	
	
	public SifrarnikLekovaDTO() {
	}
	public String getNazivLeka() {
		return nazivLeka;
	}
	public void setNazivLeka(String nazivLeka) {
		this.nazivLeka = nazivLeka;
	}
	public String getSifraLeka() {
		return sifraLeka;
	}
	public void setSifraLeka(String sifraLeka) {
		this.sifraLeka = sifraLeka;
	}
	public Integer getIdSL() {
		return idSL;
	}
	public void setIdSL(Integer idSL) {
		this.idSL = idSL;
	}
}
