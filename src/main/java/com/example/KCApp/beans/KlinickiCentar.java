package com.example.KCApp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "klinickiCentar")
@SequenceGenerator(name = "kcIdSeq", sequenceName = "kcIdGen", initialValue = 1, allocationSize = 1)
public class KlinickiCentar {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "kcIdSeq")
	@Column(name = "idKlinickogCentra", unique = true, nullable = false)
	private Integer idKlinickogCentra;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "klinickiCentar")
	private Set<AdministratorKlinickogCentra> administratoriKC = new HashSet<AdministratorKlinickogCentra>();
	
	@ManyToOne
	@JoinColumn(name = "idSL", referencedColumnName = "idSL", nullable = false)
	private SifrarnikLekova sifrarnikLekova;
	
	@ManyToOne
	@JoinColumn(name = "idSD", referencedColumnName = "idSD", nullable = false)
	private SifrarnikDijagnoza sifrarnikDijagnoza;
	
	public KlinickiCentar(Integer idKlinickogCentra, Set<AdministratorKlinickogCentra> administratoriKC,
			SifrarnikLekova sifrarnikLekova, SifrarnikDijagnoza sifrarnikDijagnoza) {
		super();
		this.idKlinickogCentra = idKlinickogCentra;
		this.administratoriKC = administratoriKC;
		this.sifrarnikLekova = sifrarnikLekova;
		this.sifrarnikDijagnoza = sifrarnikDijagnoza;
	}

}
