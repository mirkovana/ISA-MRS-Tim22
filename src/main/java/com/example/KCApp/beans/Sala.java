package com.example.KCApp.beans;

public class Sala {

	private String nazivSale;
	private int brojSale;
	
	public Sala() {}
	
	public Sala(String nazivSale, int brojSale) {
		super();
		this.nazivSale = nazivSale;
		this.brojSale = brojSale;
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
}
