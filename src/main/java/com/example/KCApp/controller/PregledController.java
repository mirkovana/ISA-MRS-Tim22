package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Pregled;
import com.example.KCApp.service.PregledService;

@RestController
@RequestMapping(value="/api")
public class PregledController {

	@Autowired
	private PregledService service;
	
	/*ISPISIVANJE PREGLEDA*/
	@GetMapping(value="/pregledi")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Pregled> getAllPregledi(Model model) {
		List<Pregled> listaPregled = service.listAll();
		model.addAttribute("listaPregled", listaPregled);
		return listaPregled;
	}
}
