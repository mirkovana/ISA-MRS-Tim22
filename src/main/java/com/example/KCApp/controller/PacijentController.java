package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.beans.Sala;
import com.example.KCApp.service.PacijentService;

@RestController
@RequestMapping(value="/api")
public class PacijentController {

	@Autowired
	private PacijentService service;
	
	/*PRIKAZ PACIJENTA PO ID-u*/
	@GetMapping(value = "/pacijenti/{idKorisnika}")
	public Pacijent findPacijentById(@PathVariable Integer idKorisnika) {
		Pacijent pacijent = service.get(idKorisnika);
		return pacijent;
	}
	
	/*PRIKAZ SVIH PACIJENATA KLINIKE*/
	@GetMapping(value="/pacijenti")
	public List<Pacijent> getAllPacijenti(Model model) {
		List<Pacijent> listaPacijenata = service.listAll();
		model.addAttribute("listaPacijenata", listaPacijenata);
		return listaPacijenata;
	}
}
