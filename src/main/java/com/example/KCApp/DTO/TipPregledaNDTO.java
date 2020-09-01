package com.example.KCApp.DTO;

import com.example.KCApp.beans.TipPregledaN;


public class TipPregledaNDTO {
	
	private Integer idTPN;
	private String nazivTPN;
	private Integer klinika;
	
	public TipPregledaNDTO() {}
	
	public TipPregledaNDTO(TipPregledaN tipPregledaN) {
		idTPN = tipPregledaN.getIdTPN();
		nazivTPN = tipPregledaN.getNazivTPN();
		klinika = tipPregledaN.getKlinika().getIdKlinike();
	}

	public Integer getIdtpn() {
		return idTPN;
	}

	public void setIdtpn(Integer idtpn) {
		this.idTPN = idtpn;
	}

	public String getNazivtpn() {
		return nazivTPN;
	}

	public void setNazivtpn(String nazivtpn) {
		this.nazivTPN = nazivtpn;
	}

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}

	public Integer getIdTPN() {
		return idTPN;
	}

	public void setIdTPN(Integer idTPN) {
		this.idTPN = idTPN;
	}

	public String getNazivTPN() {
		return nazivTPN;
	}

	public void setNazivTPN(String nazivTPN) {
		this.nazivTPN = nazivTPN;
	}
	
	

}
