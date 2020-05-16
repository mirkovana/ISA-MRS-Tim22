package com.example.KCApp.beans;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "administratorKlinike")
public class AdministratorKlinike extends User{

	private static final long serialVersionUID = 3362047507094502871L;
	
	@ManyToOne(cascade=ALL)
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	public AdministratorKlinike(String ime, String prezime, String email, String username, String password, String adresa, String grad,
			String drzava, String brojTelefona) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
	}
	
	
	public AdministratorKlinike() {
		super();
	}


	public AdministratorKlinike(String ime, String prezime, String email, String username, String password, String adresa, String grad,
			String drzava, String brojTelefona, Klinika klinika) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.klinika = klinika;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
}
