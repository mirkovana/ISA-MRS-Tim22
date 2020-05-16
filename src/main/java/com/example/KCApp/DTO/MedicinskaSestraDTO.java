package com.example.KCApp.DTO;

import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.MedicinskaSestra;
import com.example.KCApp.beans.UserTokenState;

public class MedicinskaSestraDTO extends UserDTO{
	private Integer klinika;
	//fali joj radni kalendar
	
	public MedicinskaSestraDTO(MedicinskaSestra ms) {
	}

	public MedicinskaSestraDTO(MedicinskaSestra ms, UserTokenState token) {
		super(ms.getIme(), ms.getPrezime(), ms.getEmail(), ms.getUsername(), ms.getPassword(), ms.getAdresa(), ms.getGrad(), ms.getDrzava(), ms.getBrojTelefona(), ms.getLastPasswordResetDate(), ms.getId(), token);
        klinika = ms.getKlinika().getIdKlinike();
        for(Object au : ms.getAuthorities()) {
			this.setRole(((Authority) au).getName());}
	}

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}
}
