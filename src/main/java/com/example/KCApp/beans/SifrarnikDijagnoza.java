package com.example.KCApp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@SequenceGenerator(name = "idSDSeq", sequenceName = "idSDGen", initialValue = 1, allocationSize = 1)
public class SifrarnikDijagnoza {

	@Column(name="nazivDijagnoze", unique=false, nullable=false)
	private String nazivDijagnoze;
	@Column(name="sifraDijagnoze", unique=false, nullable=false)
	private String sifraDijagnoze;
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "idSDSeq")
	@Column(name = "idSD", unique = true, nullable = false)
	private Integer idSD;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY, mappedBy = "sifrarnikDijagnoza")
	@JsonBackReference
	private Set<KlinickiCentar> klinickiCentri = new HashSet<KlinickiCentar>();
	
	public void add(KlinickiCentar item) {
	    if (item.getSifrarnikDijagnoza() != null)
	      item.getSifrarnikDijagnoza().getKlinickiCentri().remove(item);
	    item.setSifrarnikDijagnoza(this);
	    getKlinickiCentri().add(item);
	  }
	
	public SifrarnikDijagnoza() {}
	
	public SifrarnikDijagnoza(String nazivDijagnoze, String sifraDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
		this.sifraDijagnoze = sifraDijagnoze;
	}

	public SifrarnikDijagnoza(String nazivDijagnoze, String sifraDijagnoze, Integer idSD,
			Set<KlinickiCentar> klinickiCentri) {
		super();
		this.nazivDijagnoze = nazivDijagnoze;
		this.sifraDijagnoze = sifraDijagnoze;
		this.idSD = idSD;
		this.klinickiCentri = klinickiCentri;
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

	public Set<KlinickiCentar> getKlinickiCentri() {
		return klinickiCentri;
	}

	public void setKlinickiCentri(Set<KlinickiCentar> klinickiCentri) {
		this.klinickiCentri = klinickiCentri;
	}
	
	

}
