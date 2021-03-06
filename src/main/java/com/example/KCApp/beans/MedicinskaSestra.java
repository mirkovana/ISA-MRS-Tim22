package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "medicinskaSestra")
@SequenceGenerator(name = "medicinskaSestraIdSeq", sequenceName = "medicinskaSestraIdGen", initialValue = 1, allocationSize = 1)
public class MedicinskaSestra extends User {

	private static final long serialVersionUID = 924562463173998925L;

	@OneToOne(fetch = LAZY, mappedBy="medicinskaSestra")
    @JoinColumn(name = "idRadnogKalendara")	
	private RadniKalendarMS radniKalendarMS;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "medicinskaSestra")
	@JsonBackReference
	private Set<Recept> recepti = new HashSet<Recept>();
	
	public void add(Recept item) {
	    if (item.getMedicinskaSestra() != null)
	      item.getMedicinskaSestra().getRecepti().remove(item);
	    item.setMedicinskaSestra(this);
	    getRecepti().add(item);
	  }
	
	//zahtev odsustva onetomany

	public MedicinskaSestra(String ime, String prezime, String email, String username, String password, String adresa, String grad,
			String drzava, String brojTelefona) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);

	}

	public MedicinskaSestra() {
		super();
	}

	public MedicinskaSestra(String ime, String prezime, String email, String username, String password, String adresa, String grad,
			String drzava, String brojTelefona, Set<Recept> recepti, RadniKalendarMS radniKalendar) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.radniKalendarMS = radniKalendar;
		this.recepti = recepti;
	}
	
	public MedicinskaSestra(Integer id, String ime, String prezime, String email, String username, String password, String adresa, String grad,
			String drzava, String brojTelefona, Set<Recept> recepti, RadniKalendarMS radniKalendar) {
		super(id, ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.radniKalendarMS = radniKalendar;
		this.recepti = recepti;
	}
	
	public MedicinskaSestra(Klinika klinika) {
		this.klinika = klinika;
	}

	//public RadniKalendar getRadniKalendar() {
	//	return radniKalendar;
	//}

	//public void setRadniKalendar(RadniKalendar radniKalendar) {
	//	this.radniKalendar = radniKalendar;
	//}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}
	
	

}
