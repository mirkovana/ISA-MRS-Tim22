package com.example.KCApp.beans;

import java.util.Date;

public class Operacija {
	
	private Date datumOperacije;
	private Date vremeOperacije;
	private int trajanje;
	private String dodatneInfoOOperaciji;
	
	public Operacija() {}
	
	public Operacija(Date datumOperacije, Date vremeOperacije, int trajanje, String dodatneInfoOOperaciji) {
		this.datumOperacije = datumOperacije;
		this.vremeOperacije = vremeOperacije;
		this.trajanje = trajanje;
		this.dodatneInfoOOperaciji = dodatneInfoOOperaciji;
	}

	public Date getDatumOperacije() {
		return datumOperacije;
	}

	public void setDatumOperacije(Date datumOperacije) {
		this.datumOperacije = datumOperacije;
	}

	public Date getVremeOperacije() {
		return vremeOperacije;
	}

	public void setVremeOperacije(Date vremeOperacije) {
		this.vremeOperacije = vremeOperacije;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public String getDodatneInfoOOperaciji() {
		return dodatneInfoOOperaciji;
	}

	public void setDodatneInfoOOperaciji(String dodatneInfoOOperaciji) {
		this.dodatneInfoOOperaciji = dodatneInfoOOperaciji;
	}
}
