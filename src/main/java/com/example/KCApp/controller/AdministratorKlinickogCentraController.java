package com.example.KCApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.AdministratorKlinickogCentra;
import com.example.KCApp.service.AdministratorKlinickogCentraService;

@RestController
@RequestMapping(value="/api")
public class AdministratorKlinickogCentraController {
	@Autowired
	private AdministratorKlinickogCentraService service;
	
	/*PRIKAZ PACIJENTA PO ID-u*/
	@GetMapping(value = "/adminiKC/{idKorisnika}")
	public  AdministratorKlinickogCentra findPacijentById(@PathVariable Integer idKorisnika) {
		 AdministratorKlinickogCentra adminiKC = service.get(idKorisnika);
		return adminiKC;
	}
}
