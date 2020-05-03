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

import com.fasterxml.jackson.annotation.JsonBackReference;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sifrarnikDijagnoza")
@SequenceGenerator(name = "idSDSeq", sequenceName = "idSDGen", initialValue = 2, allocationSize = 1)
public class SifrarnikDijagnoza {

	@Column(name="nazivDijagnoze", unique=false, nullable=false)
	private String nazivDijagnoze;
	@Column(name="sifraDijagnoze", unique=false, nullable=false)
	private String sifraDijagnoze;
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "idSDSeq")
	@Column(name = "idSD", unique = true, nullable = false)
	private Integer idSD;
	
	
	@ManyToOne
	@JoinColumn(name = "klinickiCentar", referencedColumnName = "idKlinickogCentra", nullable = false)
	private KlinickiCentar klinickiCentar;
	
	public SifrarnikDijagnoza() {}
	
	public SifrarnikDijagnoza(String nazivDijagnoze, String sifraDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
		this.sifraDijagnoze = sifraDijagnoze;
	}

	public SifrarnikDijagnoza(String nazivDijagnoze, String sifraDijagnoze, Integer idSD,
			KlinickiCentar klinickiCentar) {
		super();
		this.nazivDijagnoze = nazivDijagnoze;
		this.sifraDijagnoze = sifraDijagnoze;
		this.idSD = idSD;
		this.klinickiCentar = klinickiCentar;
	}

	public String getNazivDijagnoze() {
		return nazivDijagnoze;
	}

	public void setNazivDijagnoze(String nazivDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
	}

	public String getSifraDijagnoze() {
		return sifraDijagnoze;
	}

	public void setSifraDijagnoze(String sifraDijagnoze) {
		this.sifraDijagnoze = sifraDijagnoze;
	}

	public Integer getIdSD() {
		return idSD;
	}

	public void setIdSD(Integer idSD) {
		this.idSD = idSD;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}


	

}
