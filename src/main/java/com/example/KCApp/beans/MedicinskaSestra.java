package com.example.KCApp.beans;

public class MedicinskaSestra extends Korisnik {
	
	private RadniKalendar radniKalendar;

	public MedicinskaSestra(String ime, String prezime, String email, String lozinka, String adresa, String grad,
			String drzava, String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);

	}

	public MedicinskaSestra(String ime, String prezime, String email, String lozinka, String adresa, String grad,
			String drzava, String brojTelefona, RadniKalendar radniKalendar) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.radniKalendar = radniKalendar;
	}

	public RadniKalendar getRadniKalendar() {
		return radniKalendar;
	}

	public void setRadniKalendar(RadniKalendar radniKalendar) {
		this.radniKalendar = radniKalendar;
	}

}
