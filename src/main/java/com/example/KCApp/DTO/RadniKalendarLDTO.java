package com.example.KCApp.DTO;

import com.example.KCApp.beans.RadniKalendarL;

public class RadniKalendarLDTO {
	private Integer idRadnogKalendara;

	public RadniKalendarLDTO() {
	}

	public RadniKalendarLDTO(RadniKalendarL radniKalendarL) {
		idRadnogKalendara = radniKalendarL.getIdRadnogKalendara();
	}

	public Integer getIdRadnogKalendara() {
		return idRadnogKalendara;
	}

	public void setIdRadnogKalendara(Integer idRadnogKalendara) {
		this.idRadnogKalendara = idRadnogKalendara;
	}
	
	

}
