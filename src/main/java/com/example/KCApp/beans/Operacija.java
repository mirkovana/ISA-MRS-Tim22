package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "operacija")
@SequenceGenerator(name = "operacijaIdSeq", sequenceName = "operacijaIdGen", initialValue = 1, allocationSize = 1)
public class Operacija {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "operacijaIdSeq")
	@Column(name = "idOperacije", unique = true, nullable = false)
	private Integer idOperacije;
	
	@Column(name="vremeOperacije", unique=false, nullable=false)
	private Date vremeOperacije;
	
	@Column(name="trajanje", unique=false, nullable=false)
	private int trajanje;
	
	@Column(name="dodatneInfoOOperaciji", unique=false, nullable=false)
	private String dodatneInfoOOperaciji;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	@ManyToOne
	@JoinColumn(name = "idSale", referencedColumnName = "idSale", nullable = false)
	private Sala sala;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	private Pacijent pacijent;
	
	@ManyToOne
	@JoinColumn(name = "idLekara", referencedColumnName = "id", nullable = false)
	private Lekar lekar;
	
	public Operacija() {}
	
	public Operacija(Integer idOperacije, Date vremeOperacije, int trajanje, 
			String dodatneInfoOOperaciji, Klinika klinika, Sala sala) {
		this.idOperacije = idOperacije;
		this.vremeOperacije = vremeOperacije;
		this.trajanje = trajanje;
		this.dodatneInfoOOperaciji = dodatneInfoOOperaciji;
		this.klinika = klinika;
		this.sala = sala;
	}

	public Date getVremeOperacije() {
		return vremeOperacije;
	}

	public void setVremeOperacije(Date vremeOperacije) {
		this.vremeOperacije = vremeOperacije;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public String getDodatneInfoOOperaciji() {
		return dodatneInfoOOperaciji;
	}

	public void setDodatneInfoOOperaciji(String dodatneInfoOOperaciji) {
		this.dodatneInfoOOperaciji = dodatneInfoOOperaciji;
	}

	public Integer getIdOperacije() {
		return idOperacije;
	}

	public void setIdOperacije(Integer idOperacije) {
		this.idOperacije = idOperacije;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}	
}
