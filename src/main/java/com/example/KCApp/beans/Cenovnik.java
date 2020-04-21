package com.example.KCApp.beans;

public class Cenovnik {
	
	//@Column(name="tipPregledaCenovnik", unique=true, nullable=false)
	private TipPregleda tipPregledaCenovnik;
	//@Column(name="cena", unique=false, nullable=false)
    private double cena;
    
    public Cenovnik() {}
    
	public Cenovnik(TipPregleda tipPregledaCenovnik, double cena) {
		this.tipPregledaCenovnik = tipPregledaCenovnik;
		this.cena = cena;
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
