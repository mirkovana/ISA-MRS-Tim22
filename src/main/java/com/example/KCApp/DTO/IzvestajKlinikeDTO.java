package com.example.KCApp.DTO;

import com.example.KCApp.beans.IzvestajKlinike;
import com.example.KCApp.beans.Klinika;


public class IzvestajKlinikeDTO {
	private Integer idIK;
	private Integer idKlinike;
	private double prosecnaOcenaKlinike;
	private double prosecnaOcenaSvakogLekara;
	
	public IzvestajKlinikeDTO(IzvestajKlinike izvestaj) {
		this.idIK = izvestaj.getIdIK();
		this.idKlinike = izvestaj.getKlinika().getIdKlinike();
		this.prosecnaOcenaKlinike = izvestaj.getProsecnaOcenaKlinike();
		this.prosecnaOcenaSvakogLekara = izvestaj.getProsecnaOcenaSvakogLekara();
	}
	
	public IzvestajKlinikeDTO() {
		
	}

	public Integer getIdIK() {
		return idIK;
	}

	public void setIdIK(Integer idIK) {
		this.idIK = idIK;
	}

	public Integer getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(Integer idKlinike) {
		this.idKlinike = idKlinike;
	}

	public double getProsecnaOcenaKlinike() {
		return prosecnaOcenaKlinike;
	}

	public void setProsecnaOcenaKlinike(double prosecnaOcenaKlinike) {
		this.prosecnaOcenaKlinike = prosecnaOcenaKlinike;
	}

	public double getProsecnaOcenaSvakogLekara() {
		return prosecnaOcenaSvakogLekara;
	}

	public void setProsecnaOcenaSvakogLekara(double prosecnaOcenaSvakogLekara) {
		this.prosecnaOcenaSvakogLekara = prosecnaOcenaSvakogLekara;
	}
	
	

}
