package com.example.KCApp.DTO;

import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Authority;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Ocena;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.UserTokenState;

public class LekarDTO extends UserDTO{
	
	private double ocena;
	private TipPregleda tipPregleda;
	private RadniKalendarLDTO radniKalendarL;
	private Integer klinika;
	
	public LekarDTO(Lekar lekar) {
	}
	
	public LekarDTO() {
		
	}

	/*public LekarDTO(Lekar lekar, UserTokenState token) {
		super(lekar.getIme(), lekar.getPrezime(), lekar.getEmail(), lekar.getUsername(), lekar.getPassword(), lekar.getAdresa(), lekar.getGrad(), lekar.getDrzava(), lekar.getBrojTelefona(), lekar.getLastPasswordResetDate(), lekar.getId(), token);
        ocena = lekar.getOcena();
        tipPregleda = lekar.getTipPregleda();
        radniKalendarL = new RadniKalendarLDTO(lekar.getRadniKalendar());
        klinika = lekar.getKlinika().getIdKlinike();
        for(Object au : lekar.getAuthorities()) {
			this.setRole(((Authority) au).getName());}
	}*/
	
	public LekarDTO(Lekar l, UserTokenState token) {
    	super(l.getIme(), l.getPrezime(), l.getEmail(), l.getUsername(), l.getPassword(), l.getAdresa(), l.getGrad(), l.getDrzava(), l.getBrojTelefona(), l.getLastPasswordResetDate(), l.getId(), token);
    	for(Object au : l.getAuthorities()) {
			this.setRole(((Authority) au).getName());}
    	klinika = l.getKlinika().getIdKlinike();
    	ocena = l.getOcena();
    	tipPregleda = l.getTipPregleda();
        radniKalendarL = new RadniKalendarLDTO(l.getRadniKalendar());

    }

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public RadniKalendarLDTO getRadniKalendarL() {
		return radniKalendarL;
	}

	public void setRadniKalendarL(RadniKalendarLDTO radniKalendarL) {
		this.radniKalendarL = radniKalendarL;
	}
}
