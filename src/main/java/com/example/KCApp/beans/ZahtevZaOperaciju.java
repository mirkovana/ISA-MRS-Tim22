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
@Table(name = "zahtevZaOperaciju")
@SequenceGenerator(name = "zahtevZaOperacijuIdSeq", sequenceName = "zahtevZaOperacijuIdGen", initialValue = 1, allocationSize = 1)
public class ZahtevZaOperaciju {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zahtevZaOperacijuIdSeq")
	@Column(name = "idZahtevaZaOperaciju", unique = true, nullable = false)
	private Integer idZahtevaZaOperaciju;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private Pacijent pacijent;
	
	//@Column(name="cena", unique=false, nullable=false)
	//private double cena;
	
	@ManyToOne
	@JoinColumn(name = "idLekara", referencedColumnName = "id", nullable = false)
	private Lekar lekar;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;

	
	@Column(name="vreme", unique=false, nullable=false)
	private String vreme;

	public ZahtevZaOperaciju() {
	}
	
	public ZahtevZaOperaciju(Integer idZahtevaZaOperaciju, Pacijent pacijent, Lekar lekar, Klinika klinika, String vreme) {
		super();
		this.idZahtevaZaOperaciju = idZahtevaZaOperaciju;
		this.pacijent = pacijent;
		this.lekar = lekar;
		this.klinika = klinika;
		this.vreme = vreme;
	}

	public Integer getIdZahtevaZaOperaciju() {
		return idZahtevaZaOperaciju;
	}

	public void setIdZahtevaZaOperaciju(Integer idZahtevaZaOperaciju) {
		this.idZahtevaZaOperaciju = idZahtevaZaOperaciju;
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

	/*public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}*/

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}


	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}
}
