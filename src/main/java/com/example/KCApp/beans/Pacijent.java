 package com.example.KCApp.beans;


import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.FetchType.EAGER;
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
public class Pacijent extends User{
	
	private static final long serialVersionUID = 7590433633631961539L;


	public static final String TABLE_NAME= "PACIJENTI";
	
	@Column(name="enabled")
	private boolean aktivan;
	
	@OneToOne(cascade = ALL, fetch = EAGER, mappedBy="pacijent")
	@JoinColumn(name = "idZdravstvenogKartona")
	private ZdravstveniKarton zdravstveniKarton;
	
	
	
	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}


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
	@JsonBackReference
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

	public Pacijent(String ime, String prezime, String email, String username, String password, String adresa, String grad, String drzava,
			String brojTelefona, ZdravstveniKarton zdravstveniKarton, int brojOsiguranika, Set<Pregled> pregledi,
			Set<ZahtevZaPregled> zahteviZP) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.zdravstveniKarton = zdravstveniKarton;
		this.brojOsiguranika = brojOsiguranika;
		this.pregledi = pregledi;
		this.zahteviZP = zahteviZP;
		
		
	}

	public Pacijent(Integer id, String ime, String prezime, String email, String username, String password, String adresa, String grad, String drzava,
			String brojTelefona, int brojOsiguranika) {
		super(id, ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.brojOsiguranika = brojOsiguranika;
	}
	
	public Pacijent(int brojOsiguranika) {
		//super(idKorisnika);
		this.brojOsiguranika = brojOsiguranika;
	}
	
	
}
