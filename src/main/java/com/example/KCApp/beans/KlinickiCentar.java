package com.example.KCApp.beans;

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

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "klinickiCentar")
@SequenceGenerator(name = "kcIdSeq", sequenceName = "kcIdGen", initialValue = 1, allocationSize = 1)
public class KlinickiCentar {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "kcIdSeq")
	@Column(name = "idKlinickogCentra", unique = true, nullable = false)
	private Integer idKlinickogCentra;
	

	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinickiCentar")
	@JsonBackReference
	private Set<AdministratorKlinickogCentra> administratoriKC = new HashSet<AdministratorKlinickogCentra>();
	
	public void add(AdministratorKlinickogCentra item) {
	    if (item.getKlinickiCentar() != null)
	      item.getKlinickiCentar().getAdministratoriKC().remove(item);
	    item.setKlinickiCentar(this);
	    getAdministratoriKC().add(item);
	  }

	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinickiCentar")
	@JsonBackReference
	private Set<SifrarnikLekova> sifrarnikLekova=new HashSet<SifrarnikLekova>();
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinickiCentar")
	@JsonBackReference
	private Set<SifrarnikDijagnoza> sifrarnikDijagnoza=new HashSet<SifrarnikDijagnoza>();
		
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinickiCentar")
	@JsonBackReference
	private Set<Klinika> klinike = new HashSet<Klinika>();
	
	public void add(Klinika item) {
	    if (item.getKlinickiCentar() != null)
	      item.getKlinickiCentar().getKlinike().remove(item);
	    item.setKlinickiCentar(this);
	    getKlinike().add(item);
	  }

	public KlinickiCentar() {
		
	}
	
	public KlinickiCentar(Integer idKlinickogCentra, Set<AdministratorKlinickogCentra> administratoriKC,
			Set<SifrarnikLekova> sifrarnikLekova, Set<SifrarnikDijagnoza> sifrarnikDijagnoza, 
			Set<Klinika> klinike) {
		super();
		this.idKlinickogCentra = idKlinickogCentra;
		this.administratoriKC = administratoriKC;
		this.sifrarnikLekova = sifrarnikLekova;
		this.sifrarnikDijagnoza = sifrarnikDijagnoza;
		this.klinike = klinike;
	}

	public Integer getIdKlinickogCentra() {
		return idKlinickogCentra;
	}

	public void setIdKlinickogCentra(Integer idKlinickogCentra) {
		this.idKlinickogCentra = idKlinickogCentra;
	}

	public Set<AdministratorKlinickogCentra> getAdministratoriKC() {
		return administratoriKC;
	}

	public void setAdministratoriKC(Set<AdministratorKlinickogCentra> administratoriKC) {
		this.administratoriKC = administratoriKC;
	}

	

	public Set<SifrarnikLekova> getSifrarnikLekova() {
		return sifrarnikLekova;
	}

	public void setSifrarnikLekova(Set<SifrarnikLekova> sifrarnikLekova) {
		this.sifrarnikLekova = sifrarnikLekova;
	}

	public Set<SifrarnikDijagnoza> getSifrarnikDijagnoza() {
		return sifrarnikDijagnoza;
	}

	public void setSifrarnikDijagnoza(Set<SifrarnikDijagnoza> sifrarnikDijagnoza) {
		this.sifrarnikDijagnoza = sifrarnikDijagnoza;
	}

	public Set<Klinika> getKlinike() {
		return klinike;
	}

	public void setKlinike(Set<Klinika> klinike) {
		this.klinike = klinike;
	}
	
	

}
