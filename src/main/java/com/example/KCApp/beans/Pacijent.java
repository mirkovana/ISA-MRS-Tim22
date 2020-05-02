 package com.example.KCApp.beans;


import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name=Pacijent.TABLE_NAME)
public class Pacijent extends Korisnik{
	
	public static final String TABLE_NAME= "PACIJENTI";
	

	@OneToOne(fetch = LAZY, mappedBy="pacijent")
	@JoinColumn(name = "idZdravstvenogKartona")
	private ZdravstveniKarton zdravstveniKarton;
	
	@Column(name="brojOsiguranika", unique=true, nullable=false)
	private int brojOsiguranika;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "pacijent")
	@JsonBackReference
	private Set<Pregled> pregledi = new HashSet<Pregled>();
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "pacijent")
	@JsonBackReference
	private Set<Operacija> operacije = new HashSet<Operacija>();
	
	public void add(Pregled item) {
	    if (item.getPacijent() != null)
	      item.getPacijent().getPregledi().remove(item);
	    item.setPacijent(this);
	    getPregledi().add(item);
	  }

	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "pacijent")
	private Set<ZahtevZaPregled> zahteviZP = new HashSet<ZahtevZaPregled>();
	
	public void add(ZahtevZaPregled item) {
	    if (item.getPacijent() != null)
	      item.getPacijent().getZahteviZP().remove(item);
	    item.setPacijent(this);
	    getZahteviZP().add(item);
	  }
	
	public Pacijent() {
	}

	public Pacijent(int brojOsiguranika, ZdravstveniKarton zdravstveniKarton, Set<Pregled> pregledi, Set<ZahtevZaPregled> zahteviZP) {
		this.brojOsiguranika = brojOsiguranika;
		this.zdravstveniKarton = zdravstveniKarton;
		this.pregledi = pregledi;
		this.zahteviZP = zahteviZP;
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


	public Set<Pregled> getPregledi() {
		return pregledi;
	}


	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}


	public Set<ZahtevZaPregled> getZahteviZP() {
		return zahteviZP;
	}

	public void setZahteviZP(Set<ZahtevZaPregled> zahteviZP) {
		this.zahteviZP = zahteviZP;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	public Pacijent(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona, ZdravstveniKarton zdravstveniKarton, int brojOsiguranika, Set<Pregled> pregledi,
			Set<ZahtevZaPregled> zahteviZP) {
		super(ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.zdravstveniKarton = zdravstveniKarton;
		this.brojOsiguranika = brojOsiguranika;
		this.pregledi = pregledi;
		this.zahteviZP = zahteviZP;
		
		
	}

	public Pacijent(Integer idKorisnika, String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
			String brojTelefona, int brojOsiguranika) {
		super(idKorisnika, ime, prezime, email, lozinka, adresa, grad, drzava, brojTelefona);
		this.brojOsiguranika = brojOsiguranika;
	}
	
	
	
	
}
