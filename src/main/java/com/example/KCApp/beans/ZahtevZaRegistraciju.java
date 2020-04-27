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
@Table(name = "zahtevZaRegistraciju")
@SequenceGenerator(name = "zahtevZaRegistracijutIdSeq", sequenceName = "zahtevZaRegistracijuIdGen", initialValue = 1, allocationSize = 1)
public class ZahtevZaRegistraciju {
	
	//neautentifikovani korisnik?
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zahtevZaRegistracijuIdSeq")
	@Column(name = "idZahtevaZaRegistraciju", unique = true, nullable = false)
	private Integer idZahtevaZaRegistraciju;
	
	@Column(name="odobren", unique=false, nullable=false)
	private boolean odobren;
	
	@ManyToOne
	@JoinColumn(name = "idKlinickogCentra", referencedColumnName = "idKlinickogCentra", nullable = false)
	private KlinickiCentar klinickiCentar;

	public ZahtevZaRegistraciju(Integer idZahtevaZaRegistraciju, boolean odobren, KlinickiCentar klinickiCentar) {
		super();
		this.idZahtevaZaRegistraciju = idZahtevaZaRegistraciju;
		this.odobren = odobren;
		this.klinickiCentar = klinickiCentar;
	}

	public Integer getIdZahtevaZaRegistraciju() {
		return idZahtevaZaRegistraciju;
	}

	public void setIdZahtevaZaRegistraciju(Integer idZahtevaZaRegistraciju) {
		this.idZahtevaZaRegistraciju = idZahtevaZaRegistraciju;
	}

	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}
	
	

}
