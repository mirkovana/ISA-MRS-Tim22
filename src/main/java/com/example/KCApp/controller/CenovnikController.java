package com.example.KCApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.Cenovnik;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.TipPregleda;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.CenovnikService;
import com.example.KCApp.service.KlinikaService;

@RestController
@RequestMapping(value="/api")
public class CenovnikController {

	@Autowired
	private CenovnikService serviceCenovnik;
	
	@Autowired
	private KlinikaService serviceKlinika;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	@GetMapping(value = "/cenovnik/{idKlinike}/{tipPregleda}")
	@PreAuthorize("hasRole('PACIJENT')")
	public Cenovnik find(@PathVariable Integer idKlinike, @PathVariable TipPregleda tipPregleda) {
		List<Cenovnik> cenovnici= new ArrayList<Cenovnik>();
		Cenovnik cen= new Cenovnik();
		Klinika k = serviceKlinika.get(idKlinike);
		cenovnici = serviceCenovnik.findAllByKlinika(k);
		for(Cenovnik c : cenovnici) {
			if(c.getTipPregledaCenovnik() == tipPregleda) {
			    cen = c;
			}
		}
		return cen;
	}
	
	@GetMapping(value = "/cenovnik/{idKlinike}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Cenovnik> prikazCenovnikaKlinike(@PathVariable Integer idKlinike) {
		List<Cenovnik> cenovnici= new ArrayList<Cenovnik>();
		Klinika k = serviceKlinika.get(idKlinike);
		cenovnici = serviceCenovnik.findAllByKlinika(k);
		return cenovnici;
	}
	
	@GetMapping(value = "/cenovnik/adminK/{idAdmina}")
	@PreAuthorize("hasRole('ADMINK')")
	public List<Cenovnik> prikazCenovnikaKlinikeAdmina(@PathVariable Integer idAdmina) {
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika k = ak.getKlinika();
		List<Cenovnik> cenovnici= new ArrayList<Cenovnik>();
		cenovnici = serviceCenovnik.findAllByKlinika(k);
		return cenovnici;
	}
}
