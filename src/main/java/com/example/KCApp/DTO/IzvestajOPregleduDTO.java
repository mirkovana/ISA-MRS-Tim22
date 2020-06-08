package com.example.KCApp.DTO;

import com.example.KCApp.beans.IzvestajOPregledu;

public class IzvestajOPregleduDTO {
	
	private Integer idIOP;
	private String nazivDijagnoze;
	private String nazivLeka;
	private String info;
	
	public IzvestajOPregleduDTO() {


	}

	public IzvestajOPregleduDTO(IzvestajOPregledu iop) {
		this.idIOP=iop.getIdIOP();
		this.nazivDijagnoze=iop.getSifrarnikDijagnoza().getNazivDijagnoze();
		this.nazivLeka=iop.getSifrarnikLekova().getNazivLeka();
		this.info=iop.getInfo();

	}

	public Integer getIdIOP() {
		return idIOP;
	}

	public void setIdIOP(Integer idIOP) {
		this.idIOP = idIOP;
	}

	public String getNazivDijagnoze() {
		return nazivDijagnoze;
	}

	public void setNazivDijagnoze(String nazivDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
	}

	public String getNazivLeka() {
		return nazivLeka;
	}

	public void setNazivLeka(String nazivLeka) {
		this.nazivLeka = nazivLeka;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
