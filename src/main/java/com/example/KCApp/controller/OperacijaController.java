package com.example.KCApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pacijent;
import com.example.KCApp.service.OperacijaService;
import com.example.KCApp.service.PacijentService;

@RestController
@RequestMapping(value="/api")
public class OperacijaController {

	@Autowired
	private OperacijaService service;
	
	@Autowired
	private PacijentService serviceP;
	
	/*ISPISIVANJE OPERACIJA*/
	@GetMapping(value="/operacije/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Operacija> getAllOperacije(Model model, @PathVariable Integer id) {
		
		Pacijent p = serviceP.get(id);
		List<Operacija> listaOperacija = service.findAllByPacijent(p);
		
		return listaOperacija;
	}
}
