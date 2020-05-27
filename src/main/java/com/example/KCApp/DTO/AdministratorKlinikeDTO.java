package com.example.KCApp.DTO;

import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.UserTokenState;

public class AdministratorKlinikeDTO extends UserDTO{
	private Integer klinika;
	
	public AdministratorKlinikeDTO(AdministratorKlinike admin) {
    }
	
    public AdministratorKlinikeDTO(AdministratorKlinike ak, UserTokenState token) {
    	super(ak.getIme(), ak.getPrezime(), ak.getEmail(), ak.getUsername(), ak.getPassword(), ak.getAdresa(), ak.getGrad(), ak.getDrzava(), ak.getBrojTelefona(), ak.getLastPasswordResetDate(), ak.getId(), token);
    	for(Object au : ak.getAuthorities()) {
			this.setRole(((Authority) au).getName());}
    	klinika = ak.getKlinika().getIdKlinike();
    }
    public AdministratorKlinikeDTO() {
    }
	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}
}
