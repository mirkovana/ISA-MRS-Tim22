package com.example.KCApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KCApp.beans.Operacija;
import com.example.KCApp.beans.Pregled;
import com.example.KCApp.service.OperacijaService;

@RestController
@RequestMapping(value="/api")
public class OperacijaController {

	@Autowired
	private OperacijaService service;
	
	/*ISPISIVANJE OPERACIJA*/
	@GetMapping(value="/operacije")
	public List<Operacija> getAllOperacije(Model model) {
		List<Operacija> listaOperacija = service.listAll();
		model.addAttribute("listaOperacija", listaOperacija);
		return listaOperacija;
	}
}
