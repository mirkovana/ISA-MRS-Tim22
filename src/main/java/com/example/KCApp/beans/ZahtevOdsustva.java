package com.example.KCApp.beans;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "zahtevOdsustva")
@SequenceGenerator(name = "zahtevOdsustvaIdSeq", sequenceName = "zahtevOdsustvaIdGen", initialValue = 1, allocationSize = 1)
public class ZahtevOdsustva {
	
	@Column(name = "razlog", unique = false, nullable = false)
	private String razlog;
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zahtevOdsustvaIdSeq")
	@Column(name = "idZahtevaOdsustva", unique = true, nullable = false)
	private Integer idZahtevaOdsustva;
	
	@Column(name = "datumPocetka", unique = false, nullable = false)
	private Date datumPocetka;
	
	@Column(name = "datumKraja", unique = false, nullable = false)
	private Date datumKraja;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private Lekar lekar;
	
	//@ManyToOne
	//@JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnika", nullable = false)
	//private MedicinskaSestra medicinskaSestra;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;

	public ZahtevOdsustva(String razlog, Integer idZahtevaOdsustva, Date datumPocetka, Date datumKraja, Lekar lekar,
			Klinika klinika) {
		this.razlog = razlog;
		this.idZahtevaOdsustva = idZahtevaOdsustva;
		this.datumPocetka = datumPocetka;
		this.datumKraja = datumKraja;
		this.lekar = lekar;
		//this.medicinskaSestra = medicinskaSestra;
		this.klinika = klinika;
	}
	
	public ZahtevOdsustva() {}

	public String getRazlog() {
		return razlog;
	}

	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}

	public Integer getIdZahtevaOdsustva() {
		return idZahtevaOdsustva;
	}

	public void setIdZahtevaOdsustva(Integer idZahtevaOdsustva) {
		this.idZahtevaOdsustva = idZahtevaOdsustva;
	}

	public Date getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(Date datumPocetak) {
		this.datumPocetka = datumPocetak;
	}

	public Date getDatumKraja() {
		return datumKraja;
	}

	public void setDatumKraja(Date datumKraja) {
		this.datumKraja = datumKraja;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	//public MedicinskaSestra getMedicinskaSestra() {
	//	return medicinskaSestra;
	//}

	//public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
	//	this.medicinskaSestra = medicinskaSestra;
	//}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	

}
