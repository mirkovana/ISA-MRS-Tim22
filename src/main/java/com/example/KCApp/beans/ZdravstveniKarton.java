package com.example.KCApp.beans;

import java.util.Map;

public class ZdravstveniKarton {
	
    private double tezina;
    private double visina;
    private double dioptrija;
    private String krvnaGrupa;
    private String alergija; //da li ostaviti da je tipa String ili List?
    private Map<TipPregleda, String> infoPregled; //da li ostaviti ovako ili nekako drugacije?
    
    public ZdravstveniKarton() {}
    
	public ZdravstveniKarton(double tezina, double visina, double dioptrija, String krvnaGrupa, String alergija,
			Map<TipPregleda, String> infoPregled) {
		super();
		this.tezina = tezina;
		this.visina = visina;
		this.dioptrija = dioptrija;
		this.krvnaGrupa = krvnaGrupa;
		this.alergija = alergija;
		this.infoPregled = infoPregled;
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

	public String getAlergija() {
		return alergija;
	}

	public void setAlergija(String alergija) {
		this.alergija = alergija;
	}

	public Map<TipPregleda, String> getInfoPregled() {
		return infoPregled;
	}

	public void setInfoPregled(Map<TipPregleda, String> infoPregled) {
		this.infoPregled = infoPregled;
	}
}
