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
@Table(name = "radniKalendar")
@SequenceGenerator(name = "radniKalendarIdSeq", sequenceName = "radniKalendarIdGen", initialValue = 1, allocationSize = 1)
public class RadniKalendar {
//dopuni atribute na osnovu modela koji nudi biblioteka
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "radniKalendarIdSeq")
	@Column(name = "idRadnogKalendara", unique = true, nullable = false)
	private Integer idRadnogKalendara;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "idKorisnika")	
	private Lekar lekar;
	
	//@OneToOne(fetch = LAZY)
	//@JoinColumn(name = "idKorisnika")	
	//private MedicinskaSestra medicinskaSestra;
}
