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
@Table(name = "izvestajOPregledu")
@SequenceGenerator(name = "idIOPSeq", sequenceName = "idIOPGen", initialValue = 1, allocationSize = 1)
public class IzvestajOPregledu {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "idIOPSeq")
	@Column(name = "idIOP", unique = true, nullable = false)
	private Integer idIOP;

	@ManyToOne
	@JoinColumn(name = "sifrarnikLekova", referencedColumnName = "idSL", nullable = true)
	private SifrarnikLekova sifrarnikLekova;

	@ManyToOne
	@JoinColumn(name = "sifrarnikDijagnoza", referencedColumnName = "idSD", nullable = true)
	private SifrarnikDijagnoza sifrarnikDijagnoza;

	@Column(name = "info", unique = false, nullable = true)
	private String info;

	@ManyToOne
	@JoinColumn(name = "pregled", referencedColumnName = "idPregleda", nullable = true)
	private Pregled pregled;

	public IzvestajOPregledu(Integer idIOP, SifrarnikLekova sifrarnikLekova, SifrarnikDijagnoza sifrarnikDijagnoza,
			String info, Pregled pregled) {

		this.idIOP = idIOP;
		this.sifrarnikLekova = sifrarnikLekova;
		this.sifrarnikDijagnoza = sifrarnikDijagnoza;
		this.info = info;
		this.pregled = pregled;
	}

	public IzvestajOPregledu() {

	}

	public Integer getIdIOP() {
		return idIOP;
	}

	public void setIdIOP(Integer idIOP) {
		this.idIOP = idIOP;
	}

	public SifrarnikLekova getSifrarnikLekova() {
		return sifrarnikLekova;
	}

	public void setSifrarnikLekova(SifrarnikLekova sifrarnikLekova) {
		this.sifrarnikLekova = sifrarnikLekova;
	}

	public SifrarnikDijagnoza getSifrarnikDijagnoza() {
		return sifrarnikDijagnoza;
	}

	public void setSifrarnikDijagnoza(SifrarnikDijagnoza sifrarnikDijagnoza) {
		this.sifrarnikDijagnoza = sifrarnikDijagnoza;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

}
