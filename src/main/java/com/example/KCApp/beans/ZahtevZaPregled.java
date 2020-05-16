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
@Table(name = "zahtevZaPregled")
@SequenceGenerator(name = "zahtevZaPregledIdSeq", sequenceName = "zahtevZaPregledIdGen", initialValue = 1, allocationSize = 1)
public class ZahtevZaPregled {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zahtevZaPregledIdSeq")
	@Column(name = "idZahtevaZaPregled", unique = true, nullable = false)
	private Integer idZahtevaZaPregled;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private Pacijent pacijent;
	
	//lekar?
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;

	public ZahtevZaPregled(Integer idZahtevaZaPregled, Pacijent pacijent, Klinika klinika) {
		super();
		this.idZahtevaZaPregled = idZahtevaZaPregled;
		this.pacijent = pacijent;
		this.klinika = klinika;
	}

	public Integer getIdZahtevaZaPregled() {
		return idZahtevaZaPregled;
	}

	public void setIdZahtevaZaPregled(Integer idZahtevaZaPregled) {
		this.idZahtevaZaPregled = idZahtevaZaPregled;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	

}
