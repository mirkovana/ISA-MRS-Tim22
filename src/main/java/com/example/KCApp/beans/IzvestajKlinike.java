package com.example.KCApp.beans;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "izvestajKlinike")
@SequenceGenerator(name = "idIKSeq", sequenceName = "idIKGen", initialValue = 1, allocationSize = 1)
public class IzvestajKlinike {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "idIKSeq")
	@Column(name = "idIK", unique = true, nullable = false)
	private Integer idIK;

	@Column(name = "prosecnaOcenaKlinike", unique = false, nullable = true)
	private double prosecnaOcenaKlinike;
	
	@Column(name = "prosecnaOcenaSvakogLekara", unique = false, nullable = true)
	private double prosecnaOcenaSvakogLekara;

	@ManyToOne
	@JoinColumn(name = "klinika", referencedColumnName = "idKlinike", nullable = true)
	private Klinika klinika;

	public IzvestajKlinike(Integer idIK, double prosecnaOcenaKlinike, double prosecnaOcenaSvakogLekara,
			Klinika klinika) {
		super();
		this.idIK = idIK;
		this.prosecnaOcenaKlinike = prosecnaOcenaKlinike;
		this.prosecnaOcenaSvakogLekara = prosecnaOcenaSvakogLekara;
		this.klinika = klinika;
	}
	
	public IzvestajKlinike() {
		
	}

	public Integer getIdIK() {
		return idIK;
	}

	public void setIdIK(Integer idIK) {
		this.idIK = idIK;
	}

	public double getProsecnaOcenaKlinike() {
		return prosecnaOcenaKlinike;
	}

	public void setProsecnaOcenaKlinike(double prosecnaOcenaKlinike) {
		this.prosecnaOcenaKlinike = prosecnaOcenaKlinike;
	}

	public double getProsecnaOcenaSvakogLekara() {
		return prosecnaOcenaSvakogLekara;
	}

	public void setProsecnaOcenaSvakogLekara(double prosecnaOcenaSvakogLekara) {
		this.prosecnaOcenaSvakogLekara = prosecnaOcenaSvakogLekara;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	

	
	
}
