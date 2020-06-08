package com.example.KCApp.DTO;

import java.util.Date;

import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.beans.ZahtevZaPregled;

public class ZahtevZaPregledDTO {
	/*private Integer idZahtevaZaPregled;
	private Integer idPacijenta;
	private int cena;
	private Integer idLekara;
	private Integer idKlinike;*/
	private TipPregleda tipPregleda;
	private String vreme;
	
	public ZahtevZaPregledDTO() {
	}

	public ZahtevZaPregledDTO(ZahtevZaPregled zzp) {
		super();
		/*this.idZahtevaZaPregled = zzp.getIdZahtevaZaPregled();
		this.idPacijenta = zzp.getPacijent().getId();
		this.cena = zzp.getCena();
		this.idLekara = zzp.getLekar().getId();
		this.idKlinike = zzp.getKlinika().getIdKlinike();*/
		this.tipPregleda = zzp.getTipPregleda();
		this.vreme = zzp.getVreme();
	}

	/*public Integer getIdZahtevaZaPregled() {
		return idZahtevaZaPregled;
	}

	public void setIdZahtevaZaPregled(Integer idZahtevaZaPregled) {
		this.idZahtevaZaPregled = idZahtevaZaPregled;
	}

	public Integer getIdPacijenta() {
		return idPacijenta;
	}

	public void setIdPacijenta(Integer idPacijenta) {
		this.idPacijenta = idPacijenta;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public Integer getIdLekara() {
		return idLekara;
	}

	public void setIdLekara(Integer idLekara) {
		this.idLekara = idLekara;
	}

	public Integer getIdKlinike() {
		return idKlinike;
	}

	public void setIdKlinike(Integer idKlinike) {
		this.idKlinike = idKlinike;
	}*/

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}
}
