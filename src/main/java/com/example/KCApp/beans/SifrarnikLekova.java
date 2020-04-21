package com.example.KCApp.beans;

public class SifrarnikLekova {
	//@Column(name="nazivLeka", unique=false, nullable=false)
	private String nazivLeka;
	//@Column(name="sifraLeka", unique=true, nullable=false)
	private String sifraLeka;
	
	public SifrarnikLekova() {}
	
	public SifrarnikLekova(String nazivLeka, String sifraLeka) {
		this.nazivLeka = nazivLeka;
		this.sifraLeka = sifraLeka;
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

}
