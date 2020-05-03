package com.example.KCApp.DTO;

import com.example.KCApp.beans.SifrarnikDijagnoza;

public class SifrarnikDijagnozaDTO {
	private String nazivDijagnoze;
	private String sifraDijagnoze;
	private Integer idSD;
	
	
	
	public SifrarnikDijagnozaDTO(SifrarnikDijagnoza sifrarnikD) {
		nazivDijagnoze = sifrarnikD.getNazivDijagnoze();
		sifraDijagnoze = sifrarnikD.getSifraDijagnoze();
		idSD = sifrarnikD.getIdSD();
	}
	public SifrarnikDijagnozaDTO() {
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
	
	
}
