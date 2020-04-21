package com.example.KCApp.beans;

public class Recept {
	
	private SifrarnikLekova sifraLeka;
	private boolean overen;
	private MedicinskaSestra msOvera;
	
	public Recept() {}
	
	public Recept(SifrarnikLekova sifraLeka, boolean overen, MedicinskaSestra msOvera) {
		this.sifraLeka = sifraLeka;
		this.overen = false;
		this.msOvera = null;
	}

	public SifrarnikLekova getSifraLeka() {
		return sifraLeka;
	}

	public void setSifraLeka(SifrarnikLekova sifraLeka) {
		this.sifraLeka = sifraLeka;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public MedicinskaSestra getMsOvera() {
		return msOvera;
	}

	public void setMsOvera(MedicinskaSestra msOvera) {
		this.msOvera = msOvera;
	}
}
