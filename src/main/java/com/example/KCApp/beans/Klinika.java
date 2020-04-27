package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.List;
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

@Entity
@Table(name = "klinika")
@SequenceGenerator(name = "klinikaIdSeq", sequenceName = "klinikaIdGen", initialValue = 1, allocationSize = 1)
public class Klinika {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "klinikaIdSeq")
	@Column(name = "idKlinike", unique = true, nullable = false)
	private Integer idKlinike;
	
	@Column(name = "nazivKlinike", unique = true, nullable = false)
	private String naziv;
	
	@Column(name = "adresaKlinike", unique = true, nullable = false)
	private String adresa;
	
	@Column(name = "gradKlinike", unique = true, nullable = false)
	private String grad;
	
	@Column(name = "opisKlinike", unique = true, nullable = false)
	private String opis;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	private Set<MedicinskaSestra> medicinskeSestre = new HashSet<MedicinskaSestra>();
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	private Set<Lekar> lekari = new HashSet<Lekar>(); 
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	private Set<Sala> sale = new HashSet<Sala>();
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "idCenovnika")
	private Cenovnik cenovnik;
	
	private Ocena ocena;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	private Set<AdministratorKlinike> administratoriKlinike = new HashSet<AdministratorKlinike>();
	
	public Klinika() {}
	
	public Klinika(String naziv, String adresa, String grad, String opis, Set<MedicinskaSestra> medicinskeSestre, Set<Lekar> lekari, Set<Sala> sale,
			Ocena ocena) {
		this.naziv = naziv;
		this.adresa = adresa;
		this.grad = grad;
		this.opis = opis;
		this.medicinskeSestre = medicinskeSestre;
		this.lekari = lekari;
		this.sale = sale;
		this.ocena = ocena;
	}

	public int getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(int idKlinike) {
		this.idKlinike = idKlinike;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public Set<MedicinskaSestra> getMedicinskeSestre() {
		return medicinskeSestre;
	}

	public void setMedicinskeSestre(Set<MedicinskaSestra> medicinskeSestre) {
		this.medicinskeSestre = medicinskeSestre;
	}

	public Set<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(Set<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Set<Sala> getSale() {
		return sale;
	}

	public void setSale(Set<Sala> sale) {
		this.sale = sale;
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}
}
