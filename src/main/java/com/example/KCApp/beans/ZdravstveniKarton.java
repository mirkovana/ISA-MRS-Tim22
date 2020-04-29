package com.example.KCApp.beans;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "zdravstveniKarton")
@SequenceGenerator(name = "zdravstveniKartonSDSeq", sequenceName = "zdravstveniKartonSDGen", initialValue = 1, allocationSize = 1)
public class ZdravstveniKarton {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "zdravstveniKartonIdSeq")
	@Column(name = "idZdravstvenogKartona", unique = true, nullable = false)
	private Integer idZdravstvenogKartona;
	
	@Column(name="tezina", unique=false, nullable=false)
    private double tezina;
	
	@Column(name="visina", unique=false, nullable=false)
    private double visina;
	
	@Column(name="dioptrija", unique=false, nullable=false)
    private double dioptrija;
	
	@Column(name="krvnaGrupa", unique=false, nullable=false)
    private String krvnaGrupa;
    //private String alergija; //da li ostaviti da je tipa String ili List?
    //private Map<TipPregleda, String> infoPregled; //da li ostaviti ovako ili nekako drugacije?
	
	@OneToOne(fetch = LAZY)//na drugu stranu
	@JoinColumn(name = "idKorisnika")	
	private Pacijent pacijent;
    
    public ZdravstveniKarton() {}
    
	public ZdravstveniKarton(double tezina, double visina, double dioptrija, String krvnaGrupa, Pacijent pacijent) {
		super();
		this.tezina = tezina;
		this.visina = visina;
		this.dioptrija = dioptrija;
		this.krvnaGrupa = krvnaGrupa;
		//this.alergija = alergija;
		//this.infoPregled = infoPregled;
		this.pacijent = pacijent;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(double dioptrija) {
		this.dioptrija = dioptrija;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public Integer getIdZdravstvenogKartona() {
		return idZdravstvenogKartona;
	}

	public void setIdZdravstvenogKartona(Integer idZdravstvenogKartona) {
		this.idZdravstvenogKartona = idZdravstvenogKartona;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	//public String getAlergija() {
	//	return alergija;
	//}

	//public void setAlergija(String alergija) {
	//	this.alergija = alergija;
	//}

	//public Map<TipPregleda, String> getInfoPregled() {
	//	return infoPregled;
	//}

	//public void setInfoPregled(Map<TipPregleda, String> infoPregled) {
	//	this.infoPregled = infoPregled;
	//}
	
	
}
