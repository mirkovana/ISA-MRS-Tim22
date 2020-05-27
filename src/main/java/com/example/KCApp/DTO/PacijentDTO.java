package com.example.KCApp.DTO;

import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.UserTokenState;

public class PacijentDTO extends UserDTO{

	private int brojOsiguranika;
	//private ZdravstveniKartonDTO zdravstveniKarton;

	public PacijentDTO(Pacijent pacijent) {
	}

	public PacijentDTO(Pacijent pacijent, UserTokenState token) {
    	super(pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail(), pacijent.getUsername(), pacijent.getPassword(), pacijent.getAdresa(), pacijent.getGrad(), pacijent.getDrzava(), pacijent.getBrojTelefona(), pacijent.getLastPasswordResetDate(), pacijent.getId(), token);
    	for(Object au : pacijent.getAuthorities()) {
			this.setRole(((Authority) au).getName());}
    	brojOsiguranika = pacijent.getBrojOsiguranika();
		//zdravstveniKarton = new ZdravstveniKartonDTO(pacijent.getZdravstveniKarton());
	}
	public PacijentDTO() {
	}
	public int getBrojOsiguranika() {
		return brojOsiguranika;
	}

	public void setBrojOsiguranika(int brojOsiguranika) {
		this.brojOsiguranika = brojOsiguranika;
	}

	/*public ZdravstveniKartonDTO getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKartonDTO zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}*/
	
	
}
