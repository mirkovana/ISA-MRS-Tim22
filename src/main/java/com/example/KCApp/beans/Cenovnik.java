package com.example.KCApp.beans;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cenovnik")
@SequenceGenerator(name = "cenovnikIdSeq", sequenceName = "cenovnikIdGen", initialValue = 1, allocationSize = 1)
public class Cenovnik {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "cenovnikIdSeq")
	@Column(name = "idCenovnika", unique = true, nullable = false)
	private Integer idCenovnika;
	
	@Column(name="TipPregleda") 
	@Enumerated(EnumType.STRING)
	private TipPregleda tipPregledaCenovnik;
	
	@Column(name="cena", unique=false, nullable=false)
    private double cena;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "idKlinike")
	private Klinika klinika;
    
    public Cenovnik() {}
    
	public Cenovnik(TipPregleda tipPregledaCenovnik, double cena) {
		this.tipPregledaCenovnik = tipPregledaCenovnik;
		this.cena = cena;
	}

	public TipPregleda getTipPregledaCenovnik() {
		return tipPregledaCenovnik;
	}

	public void setTipPregledaCenovnik(TipPregleda tipPregledaCenovnik) {
		this.tipPregledaCenovnik = tipPregledaCenovnik;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Cenovnik(Integer idCenovnika, TipPregleda tipPregledaCenovnik, double cena, Klinika klinika) {
		this.idCenovnika = idCenovnika;
		this.tipPregledaCenovnik = tipPregledaCenovnik;
		this.cena = cena;
		this.klinika = klinika;
	} 
	
	
}