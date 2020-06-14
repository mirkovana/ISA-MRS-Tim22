package com.example.KCApp.beans;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@Column(name="cena", unique=false, nullable=false)
	private double cena;
	
	@ManyToOne
	@JoinColumn(name = "idLekara", referencedColumnName = "id", nullable = false)
	private Lekar lekar;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;

	@Column(name="TipPregleda") 
	@Enumerated(EnumType.STRING)
	private TipPregleda tipPregleda;
	
	@Column(name="vreme", unique=false, nullable=false)
	private String vreme;

	public ZahtevZaPregled() {
	}
	
	public ZahtevZaPregled(Integer idZahtevaZaPregled, Pacijent pacijent, double cena, Lekar lekar, Klinika klinika,
			TipPregleda tipPregleda, String vreme) {
		super();
		this.idZahtevaZaPregled = idZahtevaZaPregled;
		this.pacijent = pacijent;
		this.cena = cena;
		this.lekar = lekar;
		this.klinika = klinika;
		this.tipPregleda = tipPregleda;
		this.vreme = vreme;
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

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}
}
