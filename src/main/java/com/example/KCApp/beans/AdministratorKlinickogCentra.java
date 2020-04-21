package com.example.KCApp.beans;

public class AdministratorKlinickogCentra extends Korisnik{

	private KlinickiCentar klinickiCentar;
	
	public AdministratorKlinickogCentra(String ime, String prezime, String email, String lozinka, String adresa,
			String grad, String drzava, String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
	}

	public AdministratorKlinickogCentra(String ime, String prezime, String email, String lozinka, String adresa,
			String grad, String drzava, String brojTelefona, KlinickiCentar klinickiCentar) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.klinickiCentar = klinickiCentar;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
}
