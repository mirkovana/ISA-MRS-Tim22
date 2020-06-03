package com.example.KCApp.DTO;

import java.util.Date;

import com.example.KCApp.beans.ZahtevOdsustva;

public class ZahtevOdsustvaDTO {

	private Integer idZahtevaOdsustva;
	private Date datumPocetka;
	private Date datumKraja;
	private String razlog;
	private Integer klinika;
	private UserDTO id;
	
public ZahtevOdsustvaDTO() {
		
	}

	public ZahtevOdsustvaDTO(ZahtevOdsustva zahtevO) {
		
		idZahtevaOdsustva = zahtevO.getIdZahtevaOdsustva();
		datumKraja=zahtevO.getDatumKraja();
		datumPocetka=zahtevO.getDatumPocetka();
		razlog=zahtevO.getRazlog();
		
		id=new UserDTO(zahtevO.getUser());
				
		
	}

	public Integer getIdZahtevaOdsustva() {
		return idZahtevaOdsustva;
	}

	public void setIdZahtevaOdsustva(Integer idZahtevaOdsustva) {
		this.idZahtevaOdsustva = idZahtevaOdsustva;
	}

	public Date getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(Date datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public Date getDatumKraja() {
		return datumKraja;
	}

	public void setDatumKraja(Date datumKraja) {
		this.datumKraja = datumKraja;
	}

	public String getRazlog() {
		return razlog;
	}

	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}

	public Integer getKlinika() {
		return klinika;
	}

	public void setKlinika(Integer klinika) {
		this.klinika = klinika;
	}

	
	
	
}
