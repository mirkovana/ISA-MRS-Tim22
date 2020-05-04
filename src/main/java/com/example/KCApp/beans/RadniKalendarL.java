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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "radniKalendarL")
@SequenceGenerator(name = "radniKalendarIdSeq", sequenceName = "radniKalendarIdGen", initialValue = 2, allocationSize = 1)
public class RadniKalendarL {
//dopuni atribute na osnovu modela koji nudi biblioteka
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "radniKalendarIdSeq")
	@Column(name = "idRadnogKalendara", unique = true, nullable = false)
	private Integer idRadnogKalendara;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "idKorisnika", referencedColumnName="idKorisnika")	
	@JsonBackReference
	private Lekar lekar;
	
	

	public Integer getIdRadnogKalendara() {
		return idRadnogKalendara;
	}

	public void setIdRadnogKalendara(Integer idRadnogKalendara) {
		this.idRadnogKalendara = idRadnogKalendara;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	
	
	
}
