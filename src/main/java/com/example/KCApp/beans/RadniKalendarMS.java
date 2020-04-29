package com.example.KCApp.beans;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "radniKalendarMS")
@SequenceGenerator(name = "radniKalendarMSIdSeq", sequenceName = "radniKalendarMSIdGen", initialValue = 1, allocationSize = 1)
public class RadniKalendarMS {
//dopuni atribute na osnovu modela koji nudi biblioteka
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "radniKalendarMSIdSeq")
	@Column(name = "idRadnogKalendara", unique = true, nullable = false)
	private Integer idRadnogKalendara;
	
	
	
	@OneToOne(fetch = LAZY )
	@JoinColumn(name = "idKorisnikaM", referencedColumnName="idKorisnika")	
	private MedicinskaSestra medicinskaSestra;

	public Integer getIdRadnogKalendara() {
		return idRadnogKalendara;
	}

	public void setIdRadnogKalendara(Integer idRadnogKalendara) {
		this.idRadnogKalendara = idRadnogKalendara;
	}

	

	public MedicinskaSestra getMedicinskaSestra() {
		return medicinskaSestra;
	}

	public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
	}
	
	
}
