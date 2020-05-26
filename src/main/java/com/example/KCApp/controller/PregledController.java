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
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.service.PregledService;

@RestController
@RequestMapping(value="/api")
public class PregledController {

	@Autowired
	private PregledService service;
	
	/*ISPISIVANJE PREGLEDA*/
	@GetMapping(value="/pregledi/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public List<Pregled> getAllPregledi(Model model, @PathVariable Integer id) {
		List<Pregled> listaPregled = service.listAll();
		for(Pregled p : listaPregled)
		{
			if(id.equals(p.getPacijent().getId()))
			{
				System.out.println("USAO SAM U IF");
				model.addAttribute("listaPregled", listaPregled);
				return listaPregled;
			}
		}
		List<Pregled> prazna = new ArrayList<Pregled>();
		return prazna;
	}
}
