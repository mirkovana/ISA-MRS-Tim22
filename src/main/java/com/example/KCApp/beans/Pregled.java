package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
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
@Table(name = "pregled")
@SequenceGenerator(name = "pregledIdSeq", sequenceName = "pregledIdGen", initialValue = 4, allocationSize = 1)
public class Pregled {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "pregledIdSeq")
	@Column(name = "idPregleda", unique = true, nullable = false)
	private Integer idPregleda;
	
	@Column(name="vreme", unique=false, nullable=false)
	private Date vreme;
	
	@Column(name="trajanje", unique=false, nullable=false)
	private int trajanje;
	
	@Column(name="cena", unique=false, nullable=false)
	private int cena;
	
	@Column(name="TipPregleda") 
	@Enumerated(EnumType.STRING)
	private TipPregleda tipPregleda;
	
	@ManyToOne
	@JoinColumn(name = "idSale", referencedColumnName = "idSale", nullable = false)
	private Sala sala;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	private Klinika klinika;
	
	@ManyToOne
	@JoinColumn(name = "idLekara", referencedColumnName = "id", nullable = false)
	private Lekar lekar;
	
	@OneToOne(fetch = LAZY, mappedBy="pregled")
    @JoinColumn(name = "idRecepta", nullable = false)	
	@JsonBackReference
	private Recept recept;
	
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = true)
	private Pacijent pacijent;
	
	
	public Pregled() {}
	
	public Pregled(Date vreme, int trajanje, TipPregleda tipPregleda, Sala sala, Lekar lekar, Klinika klinika, Recept recept, Pacijent pacijent, int cena) {
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.tipPregleda = tipPregleda;
		this.sala = sala;
		this.klinika = klinika;
		this.recept = recept;
		this.pacijent = pacijent;
		this.cena = cena;
		this.lekar = lekar;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Integer getIdPregleda() {
		return idPregleda;
	}

	public void setIdPregleda(Integer idPregleda) {
		this.idPregleda = idPregleda;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
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

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

	@Override
	public String toString() {
		return "Pregled [idPregleda=" + idPregleda + ", vreme=" + vreme + ", trajanje=" + trajanje + ", cena=" + cena
				+ ", tipPregleda=" + tipPregleda + ", sala=" + sala + ", klinika=" + klinika + ", lekar=" + lekar
				+ ", recept=" + recept + ", pacijent=" + pacijent + "]";
	}


	
	
	
}
