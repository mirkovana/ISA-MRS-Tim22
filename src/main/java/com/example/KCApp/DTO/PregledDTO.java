package com.example.KCApp.DTO;

import java.util.Date;

import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.TipPregleda;


public class PregledDTO {
	
	private Date vreme;
	private int trajanje;
	private TipPregleda tipPregleda;
	private Integer idSale;
	private Integer idKlinike;
	private Integer idLekara;
	private Integer cena;
	private Integer idPregleda;
	
	public PregledDTO() {
		
	}

	public PregledDTO(Pregled pregled) {
		super();
		this.vreme = pregled.getVreme();
		this.trajanje = pregled.getTrajanje();
		this.tipPregleda = pregled.getTipPregleda();
		this.idSale = pregled.getSala().getIdSale();
		this.cena = pregled.getCena();
		this.idLekara = pregled.getLekar().getId();
		this.idPregleda = pregled.getIdPregleda();
		this.idKlinike= pregled.getKlinika().getIdKlinike();
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Integer getIdSale() {
		return idSale;
	}

	public void setIdSale(Integer idSale) {
		this.idSale = idSale;
	}


	public Integer getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(Integer idKlinike) {
		this.idKlinike = idKlinike;
	}

	public Integer getCena() {
		return cena;
	}

	public void setCena(Integer cena) {
		this.cena = cena;
	}

	public Integer getIdLekara() {
		return idLekara;
	}

	public void setIdLekara(Integer idLekara) {
		this.idLekara = idLekara;
	}

	public Integer getIdPregleda() {
		return idPregleda;
	}

	public void setIdPregleda(Integer idPregleda) {
		this.idPregleda = idPregleda;
	}
	
	
	
	
	

}
