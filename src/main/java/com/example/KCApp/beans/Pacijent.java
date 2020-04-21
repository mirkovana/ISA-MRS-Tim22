 package com.example.KCApp.beans;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name=Pacijent.TABLE_NAME)
public class Pacijent extends Korisnik{
	public static final String TABLE_NAME= "PACIJENTI";

	private ZdravstveniKarton zdravstveniKarton;
	private int brojOsiguranika;

	public Pacijent() {
	}
	
	

	public Pacijent(int brojOsiguranika, ZdravstveniKarton zdravstveniKarton) {
		this.brojOsiguranika = brojOsiguranika;
		this.zdravstveniKarton = zdravstveniKarton;
	}

	public int getBrojOsiguranika() {
		return brojOsiguranika;
	}

	public void setBrojOsiguranika(int brojOsiguranika) {
		this.brojOsiguranika = brojOsiguranika;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}
}
