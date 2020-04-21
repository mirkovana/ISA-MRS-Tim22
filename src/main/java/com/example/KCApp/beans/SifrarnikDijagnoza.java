package com.example.KCApp.beans;

public class SifrarnikDijagnoza {

	//@Column(name="nazivDijagnoze", unique=false, nullable=false)
	private String nazivDijagnoze;
	//@Column(name="sifraDijagnoze", unique=false, nullable=false)
	private String sifraDijagnoze;
	
	public SifrarnikDijagnoza() {}
	
	public SifrarnikDijagnoza(String nazivDijagnoze, String sifraDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
		this.sifraDijagnoze = sifraDijagnoze;
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

}
