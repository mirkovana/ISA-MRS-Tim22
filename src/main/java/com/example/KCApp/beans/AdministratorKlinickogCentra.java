package com.example.KCApp.beans;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "administratorKlinickogCentra")
public class AdministratorKlinickogCentra extends User{

	private static final long serialVersionUID = 4670273082711940375L;
	
	@ManyToOne
	@JoinColumn(name = "idKlinickogCentra", referencedColumnName = "idKlinickogCentra", nullable = false)
	private KlinickiCentar klinickiCentar;
	
	public AdministratorKlinickogCentra(String ime, String prezime, String email, String username, String password, String adresa,
			String grad, String drzava, String brojTelefona) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
	}


	public AdministratorKlinickogCentra() {
		super();
	}

	public AdministratorKlinickogCentra(String ime, String prezime, String email, String username, String password, String adresa,
			String grad, String drzava, String brojTelefona, KlinickiCentar klinickiCentar) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.klinickiCentar = klinickiCentar;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
}
