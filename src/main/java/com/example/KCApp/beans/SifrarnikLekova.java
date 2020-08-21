package com.example.KCApp.beans;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "sifrarnikLekova")
@SequenceGenerator(name = "idSLSeq", sequenceName = "idSLGen", initialValue = 2, allocationSize = 1)
public class SifrarnikLekova {
	
	@Column(name="nazivLeka", unique=false, nullable=false)
	private String nazivLeka;
	
	@Column(name="sifraLeka", unique=false, nullable=false)
	private String sifraLeka;
	
	public SifrarnikLekova(String nazivLeka, String sifraLeka, Integer idSL, KlinickiCentar klinickiCentar) {
		super();
		this.nazivLeka = nazivLeka;
		this.sifraLeka = sifraLeka;
		this.idSL = idSL;
		this.klinickiCentar = klinickiCentar;
	}

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "idSLSeq")
	@Column(name = "idSL", unique = true, nullable = false)
	private Integer idSL;
	


	@ManyToOne
	@JoinColumn(name = "klinickiCentar", referencedColumnName = "idKlinickogCentra", nullable = false)
	private KlinickiCentar klinickiCentar;
	
	
	public SifrarnikLekova() {}
	
	public SifrarnikLekova(String nazivLeka, String sifraLeka) {
		this.nazivLeka = nazivLeka;
		this.sifraLeka = sifraLeka;
	}

	public String getNazivLeka() {
		return nazivLeka;
	}

	public void setNazivLeka(String nazivLeka) {
		this.nazivLeka = nazivLeka;
	}

	public String getSifraLeka() {
		return sifraLeka;
	}

	public void setSifraLeka(String sifraLeka) {
		this.sifraLeka = sifraLeka;
	}

	public Integer getIdSL() {
		return idSL;
	}

	public void setIdSL(Integer idSL) {
		this.idSL = idSL;
	}
	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
	
	

}
