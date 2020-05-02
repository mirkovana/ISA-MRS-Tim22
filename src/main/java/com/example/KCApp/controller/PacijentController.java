package com.example.KCApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.service.PacijentService;

@RestController
@RequestMapping(value="/api")
public class PacijentController {

	@Autowired
	private PacijentService service;
	
	@GetMapping(value = "/pacijenti/{idKorisnika}")
	public Pacijent findPacijentById(@PathVariable Integer idKorisnika) {
		Pacijent pacijent = service.get(idKorisnika);
		return pacijent;
	}
}
