package com.example.KCApp.beans;

public class Lekar extends Korisnik {

	private RadniKalendar radniKalendar;
	private Ocena ocena;
	
	public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
	}

	public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona, RadniKalendar radniKalendar, Ocena ocena) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.radniKalendar = radniKalendar;
		this.ocena = ocena;
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}

	public RadniKalendar getRadniKalendar() {
		return radniKalendar;
	}

	public void setRadniKalendar(RadniKalendar radniKalendar) {
		this.radniKalendar = radniKalendar;
	}
	
}
