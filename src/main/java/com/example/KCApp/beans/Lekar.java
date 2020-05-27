package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "lekar")
@SequenceGenerator(name = "lekarIdSeq", sequenceName = "lekarIdGen", initialValue = 1, allocationSize = 1)
public class Lekar extends User {

	private static final long serialVersionUID = -3774628095290548261L;

	@OneToOne(fetch = LAZY, mappedBy="lekar", cascade = ALL)
	@JoinColumn(name = "idRadnogKalendara")	
	private RadniKalendarL radniKalendarL;
	
	@Column(name="Ocena") 
	private double ocena;
	
	@Column(name="TipPregleda") 
	@Enumerated(EnumType.STRING)
	private TipPregleda tipPregleda;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "lekar")
	@JsonBackReference
	private Set<Pregled> pregledi = new HashSet<Pregled>(); 
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "lekar")
	@JsonBackReference
	private Set<Operacija> operacije = new HashSet<Operacija>(); 
	
	//klasa ZahtevZaOdmor
		
	public Lekar(String ime, String prezime, String email, String username, String password, String adresa, String grad, String drzava,
			String brojTelefona) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
	}

	public Lekar(String ime, String prezime, String email, String username, String password, String adresa, String grad, String drzava,
			String brojTelefona, RadniKalendarL radniKalendarL, double ocena, TipPregleda tipPregleda) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona);
		this.radniKalendarL = radniKalendarL;
		this.ocena = ocena;
		this.tipPregleda = tipPregleda;
	}

	public Lekar() {
		super();
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	
	public void prosecnaOcena(double ocena1) {
		this.ocena = (this.ocena + ocena1) / 2;
	}

	public RadniKalendarL getRadniKalendar() {
		return radniKalendarL;
	}

	public void setRadniKalendar(RadniKalendarL radniKalendarL) {
		this.radniKalendarL = radniKalendarL;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
	
}
