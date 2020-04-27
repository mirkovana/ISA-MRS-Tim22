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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "sala")
@SequenceGenerator(name = "salaIdSeq", sequenceName = "salaIdGen", initialValue = 1, allocationSize = 1)
public class Sala {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "salaIdSeq")
	@Column(name = "idSale", unique = true, nullable = false)
	private Integer idSale;

	@Column(name="nazivSale", unique=false, nullable=false)
	private String nazivSale;
	
	@Column(name="brojSale", unique=true, nullable=false)
	private int brojSale;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "sala")
	private Set<Operacija> operacije = new HashSet<Operacija>();
	
	public void add(Operacija item) {
	    if (item.getSala() != null)
	      item.getSala().getOperacije().remove(item);
	    item.setSala(this);
	    getOperacije().add(item);
	  }
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "sala")
	private Set<Pregled> pregledi = new HashSet<Pregled>();
	
	public void add(Pregled item) {
	    if (item.getSala() != null)
	      item.getSala().getPregledi().remove(item);
	    item.setSala(this);
	    getPregledi().add(item);
	  }
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	public Sala() {}
	
	public Sala(String nazivSale, int brojSale, Integer idSale, Set<Operacija> operacije, Set<Pregled> pregledi, Klinika klinika) {
		super();
		this.nazivSale = nazivSale;
		this.brojSale = brojSale;
		this.idSale = idSale;
		this.operacije = operacije;
		this.pregledi = pregledi;
		this.klinika = klinika;
	}

	public String getNazivSale() {
		return nazivSale;
	}

	public void setNazivSale(String nazivSale) {
		this.nazivSale = nazivSale;
	}

	public int getBrojSale() {
		return brojSale;
	}

	public void setBrojSale(int brojSale) {
		this.brojSale = brojSale;
	}

	public Integer getIdSale() {
		return idSale;
	}

	public void setIdSale(Integer idSale) {
		this.idSale = idSale;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	
	
}
