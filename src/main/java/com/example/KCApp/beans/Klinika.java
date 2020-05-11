package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "klinika")
@SequenceGenerator(name = "klinikaIdSeq", sequenceName = "klinikaIdGen", initialValue = 3, allocationSize = 1)
public class Klinika {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "klinikaIdSeq")
	@Column(name = "idKlinike", unique = true, nullable = true)
	private Integer idKlinike;
	
	@Column(name = "nazivKlinike", unique = false, nullable = true)
	private String naziv;
	
	@Column(name = "adresaKlinike", unique = false, nullable = true)
	private String adresa;
	
	@Column(name = "gradKlinike", unique = false, nullable = true)
	private String grad;
	
	@Column(name = "opisKlinike", unique = false, nullable = true)
	private String opis;
	
	
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	@JsonBackReference
	private Set<Lekar> lekari = new HashSet<Lekar>(); 
	
	public void add(Lekar item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getLekari().remove(item);
	    item.setKlinika(this);
	    getLekari().add(item);
	  }
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	@JsonBackReference
	private Set<MedicinskaSestra> medicinskeSestre = new HashSet<MedicinskaSestra>();
	
	public void add(MedicinskaSestra item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getMedicinskeSestre().remove(item);
	    item.setKlinika(this);
	    getMedicinskeSestre().add(item);
	  }
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	private Set<Sala> sale = new HashSet<Sala>();
	
	public void add(Sala item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getSale().remove(item);
	    item.setKlinika(this);
	    getSale().add(item);
	  }
	
	@Column(name="Ocena") 
	@Enumerated(EnumType.STRING)
	private Ocena ocena;
	
	@OneToOne(cascade = ALL, fetch = EAGER)
	@JoinColumn(name = "idCenovnika")
	private Cenovnik cenovnik;
	
	

	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	@JsonBackReference
	private Set<ZahtevOdsustva> zahteviOdsustva = new HashSet<ZahtevOdsustva>();
	
	public void add(ZahtevOdsustva item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getZahteviOdsustva().remove(item);
	    item.setKlinika(this);
	    getZahteviOdsustva().add(item);
	  }
	
	@OneToMany(cascade = {ALL}, fetch = EAGER, mappedBy = "klinika")
	@JsonBackReference
	private Set<AdministratorKlinike> administratoriKlinike = new HashSet<AdministratorKlinike>();
	
	public void add(AdministratorKlinike item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getAdministratoriKlinike().remove(item);
	    item.setKlinika(this);
	    getAdministratoriKlinike().add(item);
	  }
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	@JsonBackReference
	private Set<Operacija> operacije = new HashSet<Operacija>();
	
	public void add(Operacija item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getOperacije().remove(item);
	    item.setKlinika(this);
	    getOperacije().add(item);
	  }
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	@JsonBackReference
	private Set<Pregled> pregledi = new HashSet<Pregled>();
	
	public void add(Pregled item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getPregledi().remove(item);
	    item.setKlinika(this);
	    getPregledi().add(item);
	  }
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinika")
	@JsonBackReference
	private Set<ZahtevZaPregled> zahteviZaPregled = new HashSet<ZahtevZaPregled>();
	
	public void add(ZahtevZaPregled item) {
	    if (item.getKlinika() != null)
	      item.getKlinika().getZahteviZaPregled().remove(item);
	    item.setKlinika(this);
	    getZahteviZaPregled().add(item);
	  }
	
	@ManyToOne
	@JoinColumn(name = "idKlinickogCentra", referencedColumnName = "idKlinickogCentra", nullable = false)
	private KlinickiCentar klinickiCentar;
	
	public Klinika() {}
	
	public Klinika(String naziv, String adresa, String grad, String opis, Set<MedicinskaSestra> medicinskeSestre, Set<Lekar> lekari, Set<Sala> sale,
			Ocena ocena, Set<ZahtevOdsustva> zahteviOdsustva, Set<Operacija> operacije, Set<Pregled> pregledi, 
			Set<ZahtevZaPregled> zahteviZaPregled, KlinickiCentar klinickiCentar) {
		this.naziv = naziv;
		this.adresa = adresa;
		this.grad = grad;
		this.opis = opis;
		this.medicinskeSestre = medicinskeSestre;
		this.lekari = lekari;
		this.sale = sale;
		this.ocena = ocena;
		this.zahteviOdsustva = zahteviOdsustva;
		this.operacije = operacije;
		this.pregledi = pregledi;
		this.zahteviZaPregled = zahteviZaPregled;
		this.klinickiCentar = klinickiCentar;
	}

	public Integer getIdKlinike() {
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

	public Set<ZahtevOdsustva> getZahteviOdsustva() {
		return zahteviOdsustva;
	}

	public void setZahteviOdsustva(Set<ZahtevOdsustva> zahteviOdsustva) {
		this.zahteviOdsustva = zahteviOdsustva;
	}

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

	public Set<AdministratorKlinike> getAdministratoriKlinike() {
		return administratoriKlinike;
	}

	public void setAdministratoriKlinike(Set<AdministratorKlinike> administratoriKlinike) {
		this.administratoriKlinike = administratoriKlinike;
	}

	public void setIdKlinike(Integer idKlinike) {
		this.idKlinike = idKlinike;
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

	public Set<ZahtevZaPregled> getZahteviZaPregled() {
		return zahteviZaPregled;
	}

	public void setZahteviZaPregled(Set<ZahtevZaPregled> zahteviZaPregled) {
		this.zahteviZaPregled = zahteviZaPregled;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
	
	
	
}
