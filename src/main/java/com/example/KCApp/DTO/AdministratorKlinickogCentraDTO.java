package com.example.KCApp.DTO;

import java.sql.Timestamp;

import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.UserTokenState;

public class AdministratorKlinickogCentraDTO extends UserDTO{

	/*public AdministratorKlinickogCentraDTO(String ime, String prezime, String email, String username, String password, String adresa,
			String grad, String drzava, String brojTelefona, Timestamp lastPasswordResetDate, Integer id,
			UserTokenState token) {
		super(ime, prezime, email, username, password, adresa, grad, drzava, brojTelefona, lastPasswordResetDate, id, token);
	}*/

	public AdministratorKlinickogCentraDTO(AdministratorKlinickogCentra admin) {
    }
	
    public AdministratorKlinickogCentraDTO(AdministratorKlinickogCentra akc, UserTokenState token) {
    	super(akc.getIme(), akc.getPrezime(), akc.getEmail(), akc.getUsername(), akc.getPassword(), akc.getAdresa(), akc.getGrad(), akc.getDrzava(), akc.getBrojTelefona(), akc.getLastPasswordResetDate(), akc.getId(), token);
    	for(Object au : akc.getAuthorities()) {
			this.setRole(((Authority) au).getName());}
    }
	
}
