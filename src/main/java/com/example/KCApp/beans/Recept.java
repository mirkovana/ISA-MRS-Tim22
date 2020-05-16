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
@Table(name = "recept")
@SequenceGenerator(name = "receptIdSeq", sequenceName = "receptIdGen", initialValue = 1, allocationSize = 1)
public class Recept {
	
	//private SifrarnikLekova sifraLeka;
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "receptIdSeq")
	@Column(name = "idRecepta", unique = true, nullable = false)
	private Integer idRecepta;
	
	@Column(name="overen", unique=false, nullable=false)
	private boolean overen;
	
	@ManyToOne //da li ovo ili ima nesto za 0
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private MedicinskaSestra medicinskaSestra;
	
	@ManyToOne
	@JoinColumn(name = "idPregleda", referencedColumnName = "idPregleda", nullable = false)
	private Pregled pregled;
	
	public Recept() {}
	
	public Recept(boolean overen, MedicinskaSestra medicinskaSestra, Pregled pregled, Integer idRecepta) {
		//this.sifraLeka = sifraLeka;
		this.overen = false;
		this.medicinskaSestra = null;
		this.pregled = pregled;
		this.idRecepta = idRecepta;
	}

	//public SifrarnikLekova getSifraLeka() {
	//	return sifraLeka;
	//}

	//public void setSifraLeka(SifrarnikLekova sifraLeka) {
	//	this.sifraLeka = sifraLeka;
	//}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public MedicinskaSestra getMedicinskaSestra() {
		return medicinskaSestra;
	}

	public void setMsOvera(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Integer getIdRecepta() {
		return idRecepta;
	}

	public void setIdRecepta(Integer idRecepta) {
		this.idRecepta = idRecepta;
	}

	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}
	
	
	
}
