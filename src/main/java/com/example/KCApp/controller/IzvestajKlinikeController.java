package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.DTO.IzvestajKlinikeDTO;
import com.example.KCApp.DTO.IzvestajOPregleduDTO;
import com.example.KCApp.beans.AdministratorKlinike;
import com.example.KCApp.beans.IzvestajKlinike;
import com.example.KCApp.beans.IzvestajOPregledu;
import com.example.KCApp.beans.Klinika;
import com.example.KCApp.beans.Lekar;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.beans.SifrarnikDijagnoza;
import com.example.KCApp.beans.SifrarnikLekova;
import com.example.KCApp.service.AdministratorKlinikeService;
import com.example.KCApp.service.IzvestajKlinikeService;
import com.example.KCApp.service.KlinikaService;
import com.example.KCApp.service.LekarService;

public class IzvestajKlinikeController {
	
	@Autowired
	private IzvestajKlinikeService service;
	
	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private AdministratorKlinikeService serviceAK;
	
	@Autowired
	private LekarService serviceL;
	
	@PostMapping(value= "/izvestajKlinike/admink/{idAdmina}",consumes = "application/json")
	@PreAuthorize("hasRole('ADMINK')")
	public IzvestajKlinike izvestaj(@PathVariable Integer idAdmina) {
		
		AdministratorKlinike ak = serviceAK.get(idAdmina);
		Klinika klinikaAK = ak.getKlinika();
		
		IzvestajKlinike ik = new IzvestajKlinike();
		
		ik.setProsecnaOcenaKlinike(klinikaAK.getOcena());	

		System.out.println("IZVESTAJ " + ik);
		return ik;
		}
}
