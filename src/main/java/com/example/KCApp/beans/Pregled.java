package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pregled")
@SequenceGenerator(name = "pregledIdSeq", sequenceName = "pregledIdGen", initialValue = 1, allocationSize = 1)
public class Pregled {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "pregledIdSeq")
	@Column(name = "idPregleda", unique = true, nullable = false)
	private Integer idPregleda;
	
	@Column(name="vreme", unique=false, nullable=false)
	private Date vreme;
	
	@Column(name="trajanje", unique=false, nullable=false)
	private int trajanje;
	
	@Column(name="TipPregleda") 
	@Enumerated(EnumType.STRING)
	private TipPregleda tipPregleda;
	
	@ManyToOne
	@JoinColumn(name = "idSale", referencedColumnName = "idSale", nullable = false)
	private Sala sala;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "pregled")
	private Set<Recept> recepti = new HashSet<Recept>();
	
	public void add(Recept item) {
	    if (item.getPregled() != null)
	      item.getPregled().getRecepti().remove(item);
	    item.setPregled(this);
	    getRecepti().add(item);
	  }
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private Pacijent pacijent;
	
	
	public Pregled() {}
	
	public Pregled(Date vreme, int trajanje, TipPregleda tipPregleda, Sala sala, Klinika klinika, Set<Recept> recepti, Pacijent pacijent) {
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.tipPregleda = tipPregleda;
		this.sala = sala;
		this.klinika = klinika;
		this.recepti = recepti;
		this.pacijent = pacijent;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Integer getIdPregleda() {
		return idPregleda;
	}

	public void setIdPregleda(Integer idPregleda) {
		this.idPregleda = idPregleda;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

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

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
	
	
}
