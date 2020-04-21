package com.example.KCApp.beans;

import java.util.Date;

public class Pregled {

	private Date datum;
	private Date vreme;
	private int trajanje;
	private TipPregleda tipPregleda;
	
	public Pregled() {}
	
	public Pregled(Date datum, Date vreme, int trajanje, TipPregleda tipPregleda) {
		this.datum = datum;
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.tipPregleda = tipPregleda;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
	
}
