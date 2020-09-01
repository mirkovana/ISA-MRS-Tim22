package com.example.KCApp.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "tippregledan")
@SequenceGenerator(name = "tipPregledaNIdSeq", sequenceName = "tipPregledaNIdGen", initialValue = 3, allocationSize = 1)
public class TipPregledaN {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "tipPregledaNIdSeq")
	@Column(name = "idTPN", unique = true, nullable = false)
	private Integer idTPN;
	
	@Column(name="nazivTPN", unique=false, nullable=false)
	private String nazivTPN;
	
	@ManyToOne
	@JoinColumn(name = "idKlinike", referencedColumnName = "idKlinike", nullable = false)
	@JsonBackReference	private Klinika klinika;

	public TipPregledaN(Integer idTPN, String nazivTPN, Klinika klinika) {
		super();
		this.idTPN = idTPN;
		this.nazivTPN = nazivTPN;
		this.klinika = klinika;
	}
	
	public TipPregledaN() {}

	public Integer getIdTPN() {
		return idTPN;
	}

	public void setIdTPN(Integer idTPN) {
		this.idTPN = idTPN;
	}

	public String getNazivTPN() {
		return nazivTPN;
	}

	public void setNazivTPN(String nazivTPN) {
		this.nazivTPN = nazivTPN;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	

}
