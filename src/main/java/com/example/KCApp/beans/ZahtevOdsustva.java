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
@SequenceGenerator(name = "zahtevOdsustvaIdSeq", sequenceName = "zahtevOdsustvaIdGen", initialValue = 2, allocationSize = 1)
public class ZahtevOdsustva {
	
	@Column(name = "razlog", unique = false, nullable = false)
	private String razlog;
	
	@Column(name = "odobren", unique = false, nullable = true)
	private boolean odobren;
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zahtevOdsustvaIdSeq")
	@Column(name = "idZahtevaOdsustva", unique = true, nullable = false)
	private Integer idZahtevaOdsustva;
	
	@Column(name = "datumPocetka", unique = false, nullable = false)
	private Date datumPocetka;
	
	@Column(name = "datumKraja", unique = false, nullable = false)
	private Date datumKraja;
	
	private boolean zavrseno;
	
//	@ManyToOne
//	@JoinColumn(name = "idLekara", referencedColumnName = "id", nullable = false)
//	private Lekar lekar;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private User user;
	
//	@ManyToOne
//	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
//	private Integer klinika;

	public ZahtevOdsustva(String razlog,boolean odobren, Integer idZahtevaOdsustva, Date datumPocetka, Date datumKraja, User user, boolean zavrseno
			) {
		this.razlog = razlog;
		this.odobren=odobren;
		this.idZahtevaOdsustva = idZahtevaOdsustva;
		this.datumPocetka = datumPocetka;
		this.datumKraja = datumKraja;
		this.user = user;
		this.zavrseno = zavrseno;
		//this.medicinskaSestra = medicinskaSestra;
		//this.klinika = klinika;
	}
	
	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isZavrseno() {
		return zavrseno;
	}

	public void setZavrseno(boolean zavrseno) {
		this.zavrseno = zavrseno;
	}
	
	

//	public Integer getKlinika() {
//		return klinika;
//	}
//
//	public void setKlinika(Integer klinika) {
//		this.klinika = klinika;
//	}

	

	//public MedicinskaSestra getMedicinskaSestra() {
	//	return medicinskaSestra;
	//}

	//public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
	//	this.medicinskaSestra = medicinskaSestra;
	//}


	

}
